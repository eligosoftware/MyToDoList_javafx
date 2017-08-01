package com.eligosoftware.todolist;

import com.eligosoftware.todolist.dataModel.TodoItem;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

public class Controller {
    private List<TodoItem> todoItems;
    @FXML
    private ListView todoListView;

    public void initialize(){
       TodoItem item1=new TodoItem("Mail Birthday Card",
               "Buy a 30th birthday card for John", LocalDate.of(2017, Month.AUGUST,25));
        TodoItem item2=new TodoItem("Doctor's Appointment",
                "AAAAAAAAAAAAAA1111111111111111111", LocalDate.of(2017, Month.AUGUST,15));
        TodoItem item3=new TodoItem("B2",
                "BBBBBBBBBBBBBB2222222222222222", LocalDate.of(2017, Month.JULY,22));
        TodoItem item4=new TodoItem("C3",
                "CCCCCCCCCC3333333333333", LocalDate.of(2017, Month.SEPTEMBER,3));
        TodoItem item5=new TodoItem("D4",
                "DDDDDDDDDDDDDDDDDDDD4444444444", LocalDate.of(2017, Month.JANUARY,12));

        todoItems=new ArrayList<>();
        todoItems.add(item1);
        todoItems.add(item2);
        todoItems.add(item3);
        todoItems.add(item4);
        todoItems.add(item5);

        todoListView.getItems().setAll(todoItems);
        todoListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    }
}
