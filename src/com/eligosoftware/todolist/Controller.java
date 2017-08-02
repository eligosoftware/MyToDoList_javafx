package com.eligosoftware.todolist;

import com.eligosoftware.todolist.dataModel.ToDoData;
import com.eligosoftware.todolist.dataModel.TodoItem;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextArea;

import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Controller {
    private List<TodoItem> todoItems;
    @FXML
    private ListView<TodoItem> todoListView;

    @FXML
    private TextArea detailsTextView;

    @FXML
    private Label deadlineLabel;

    public void initialize(){
//       TodoItem item1=new TodoItem("Mail Birthday Card",
//               "Buy a 30th birthday card for John", LocalDate.of(2017, Month.AUGUST,25));
//        TodoItem item2=new TodoItem("Doctor's Appointment",
//                "AAAAAAAAAAAAAA1111111111111111111", LocalDate.of(2017, Month.AUGUST,15));
//        TodoItem item3=new TodoItem("B2",
//                "BBBBBBBBBBBBBB2222222222222222", LocalDate.of(2017, Month.JULY,22));
//        TodoItem item4=new TodoItem("C3",
//                "CCCCCCCCCC3333333333333", LocalDate.of(2017, Month.SEPTEMBER,3));
//        TodoItem item5=new TodoItem("D4",
//                "DDDDDDDDDDDDDDDDDDDD4444444444", LocalDate.of(2017, Month.JANUARY,12));

        //todoItems= ToDoData.getInstance().getTodoItems();//new ArrayList<>();
//        todoItems.add(item1);
//        todoItems.add(item2);
//        todoItems.add(item3);
//        todoItems.add(item4);
//        todoItems.add(item5);

      //  ToDoData.getInstance().setTodoItems(todoItems);

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
