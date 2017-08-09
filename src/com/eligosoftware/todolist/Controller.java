package com.eligosoftware.todolist;

import com.eligosoftware.todolist.dataModel.ToDoData;
import com.eligosoftware.todolist.dataModel.TodoItem;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

public class Controller {
    private List<TodoItem> todoItems;
    @FXML
    private ListView<TodoItem> todoListView;

    @FXML
    private TextArea detailsTextView;

    @FXML
    private Label deadlineLabel;

    @FXML
    private BorderPane mainBorderPane;

    public void initialize(){
        todoListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<TodoItem>() {
            @Override
            public void changed(ObservableValue<? extends TodoItem> observable, TodoItem oldValue, TodoItem newValue) {
                if(newValue!=null){
                    TodoItem item=todoListView.getSelectionModel().getSelectedItem();
                    detailsTextView.setText(item.getDetails());
                    DateTimeFormatter df=DateTimeFormatter.ofPattern("MMMM d, yyyy");

                    deadlineLabel.setText(df.format(item.getDeadline()));
                }
            }
        });

        todoListView.getItems().setAll(ToDoData.getInstance().getTodoItems());
        todoListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        todoListView.getSelectionModel().selectFirst();
    }


    @FXML
    public void showNewItemDialog(){
        Dialog<ButtonType> dialog=new Dialog<>();
        dialog.initOwner(mainBorderPane.getScene().getWindow());
        dialog.setTitle("Add New Todo Item");
        dialog.setHeaderText("CDFcvmk mdfk m km df");
        FXMLLoader fxmlLoader=new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("todoItemDialog.fxml"));
        try{

            dialog.getDialogPane().setContent(fxmlLoader.load());
        } catch (IOException e){
            System.out.println("Couln't load dialogue");
            e.printStackTrace();
            return;
        }
        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

        Optional<ButtonType> result=dialog.showAndWait();
        if(result.isPresent()&& result.get()==ButtonType.OK){
            DialogController controller=fxmlLoader.getController();
            TodoItem item=controller.processResults();
            todoListView.getItems().setAll(ToDoData.getInstance().getTodoItems());
            if(item!=null){
                todoListView.getSelectionModel().select(item);
            }
            System.out.println("OK pressed");
        } else{
            System.out.println("Cancel Pressed");
        }

    }

    @FXML
    public void handleClickListView(){
        TodoItem item=todoListView.getSelectionModel().getSelectedItem();
       // System.out.println("The selected item is "+item);
//        StringBuilder sb=new StringBuilder(item.getDetails());
//        sb.append("\n\n\n\n");
//        sb.append("Due: ");
//        sb.append(item.getDeadline().toString());

        detailsTextView.setText(item.getDetails());
        deadlineLabel.setText(item.getDeadline().toString());



    }
}
