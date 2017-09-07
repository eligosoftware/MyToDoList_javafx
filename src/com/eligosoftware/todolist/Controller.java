package com.eligosoftware.todolist;

import com.eligosoftware.todolist.dataModel.ToDoData;
import com.eligosoftware.todolist.dataModel.TodoItem;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.util.Callback;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalUnit;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public class Controller {

    @FXML
    private ListView<TodoItem> todoListView;

    @FXML
    private TextArea detailsTextView;

    @FXML
    private Label deadlineLabel;

    @FXML
    private BorderPane mainBorderPane;

    @FXML
    private ContextMenu listContextMenu;

    @FXML
    private ToggleButton filterToggleButton;

    private FilteredList filteredList;

    private Predicate<TodoItem> wantAllItems;
    private Predicate<TodoItem> wantTodayItems;


    private void updateView(TodoItem item) {
        if (item != null){
            detailsTextView.setText(item.getDetails());
        DateTimeFormatter df = DateTimeFormatter.ofPattern("MMMM d, yyyy");
        deadlineLabel.setText(df.format(item.getDeadline()));

    } else {
            detailsTextView.setText(null);
            deadlineLabel.setText(null);
        }
     //     todoListView.getItems().set(todoListView.getSelectionModel().getSelectedIndex(),item);
       // todoListView.getItems().set(todoListView.getSelectionModel().getSelectedIndex(),item);

    }
    public void initialize(){

        listContextMenu=new ContextMenu();

        MenuItem deleteMenuItem=new MenuItem("Delete");
        deleteMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                TodoItem item=todoListView.getSelectionModel().getSelectedItem();
                deleteItem(item);
                if(todoListView.getItems().size() ==0) {
                    updateView(null);
                }
            }
        });
        listContextMenu.getItems().addAll(deleteMenuItem);

        todoListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<TodoItem>() {
            @Override
            public void changed(ObservableValue<? extends TodoItem> observable, TodoItem oldValue, TodoItem newValue) {
                if(newValue!=null){
                    TodoItem item=todoListView.getSelectionModel().getSelectedItem();
                    updateView(item);
                }
            }
        });

        wantAllItems=new Predicate<TodoItem>() {
            @Override
            public boolean test(TodoItem item) {
                return true;
            }
        };
        wantTodayItems=new Predicate<TodoItem>() {
            @Override
            public boolean test(TodoItem item) {
                return item.getDeadline().equals(LocalDate.now());
            }
        };

        filteredList=new FilteredList(ToDoData.getInstance().getTodoItems(),
               wantAllItems);
        SortedList<TodoItem> sortedList=new SortedList<TodoItem>(filteredList, new Comparator<TodoItem>() {
            @Override
            public int compare(TodoItem o1, TodoItem o2) {
                return o1.getDeadline().compareTo(o2.getDeadline());
            }
        });

        todoListView.setItems(sortedList);
        todoListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        todoListView.getSelectionModel().selectFirst();

        todoListView.setCellFactory(new Callback<ListView<TodoItem>, ListCell<TodoItem>>() {
            @Override
            public ListCell<TodoItem> call(ListView<TodoItem> param) {
                ListCell<TodoItem> cell=new ListCell<TodoItem>(){
                    @Override
                    protected void updateItem(TodoItem item, boolean empty) {
                        super.updateItem(item, empty);
                        if(empty){
                           setText(null);
                        } else{
                            setText(item.getShortDescription());
                            if(item.getDeadline().isBefore(LocalDate.now().plusDays(1))){
                                setTextFill(Color.RED);
                            }
                            else if(item.getDeadline().equals(LocalDate.now().plusDays(1))){
                                setTextFill(Color.GREEN);
                            }
                        }
                    }
                };

                cell.emptyProperty().addListener(
                        (obs,wasEmpty,isNowEmpty)->{
                            if(isNowEmpty){
                                cell.setContextMenu(null);
                            } else {
                                cell.setContextMenu(listContextMenu);
                            }
                }
                );
                return cell;
            }
        });
    }

    private void deleteItem(TodoItem item) {
        Alert alert=new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Todo item");
        alert.setHeaderText("Delete item "+item.getShortDescription());
        alert.setContentText("Are you sure? Press OK to confirm, or Cancel to Back Out");
        Optional<ButtonType> result=alert.showAndWait();
        if(result.isPresent()&&result.get()==ButtonType.OK){
            ToDoData.getInstance().deleteTodoItem(item);
        }
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
            todoListView.getSelectionModel().select(item);
        }

    }

    private void resortlist(){
        SortedList<TodoItem> sortedList=new SortedList<TodoItem>(ToDoData.getInstance().getTodoItems(), new Comparator<TodoItem>() {
            @Override
            public int compare(TodoItem o1, TodoItem o2) {
                return o1.getDeadline().compareTo(o2.getDeadline());
            }
        });

        todoListView.setItems(sortedList);
    }
    @FXML
    public void showEditItemDialog(){
        Dialog<ButtonType> dialog=new Dialog<>();
        dialog.initOwner(mainBorderPane.getScene().getWindow());
        dialog.setTitle("Edit Todo Item");
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
        DialogController controller=fxmlLoader.getController();
        TodoItem currentItem=todoListView.getSelectionModel().getSelectedItem();


        controller.setItem(currentItem);

        Optional<ButtonType> result=dialog.showAndWait();
        if(result.isPresent()&& result.get()==ButtonType.OK){
            controller.updateItem();

            updateView(currentItem);
        }
        resortlist();
    }

    @FXML
    public void handleKeyPressed(KeyEvent event){
        TodoItem selectedItem=todoListView.getSelectionModel().getSelectedItem();
        if(selectedItem!=null){
            if(event.getCode().equals(KeyCode.DELETE)){
                deleteItem(selectedItem);
            }
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
    @FXML
    public void handleFilterButton(){
        TodoItem selectedItem=todoListView.getSelectionModel().getSelectedItem();
        if(filterToggleButton.isSelected()) {
            filteredList.setPredicate(wantTodayItems);
            if(filteredList.isEmpty()){
                detailsTextView.clear();
                deadlineLabel.setText("");
            }
            else if(filteredList.contains(selectedItem)){
                todoListView.getSelectionModel().select(selectedItem);
            }
            else {
                todoListView.getSelectionModel().selectFirst();
            }
        }
        else{
            filteredList.setPredicate(wantAllItems);
            todoListView.getSelectionModel().select(selectedItem);
        }
    }
    @FXML
    public void handleExit(){
        Platform.exit();
    }
}
