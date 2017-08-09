package com.eligosoftware.todolist;

import com.eligosoftware.todolist.dataModel.ToDoData;
import com.eligosoftware.todolist.dataModel.TodoItem;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.time.LocalDate;

public class DialogController {

    @FXML
    public TextField shortDesctiptionField;

    @FXML
    public TextArea detailsField;

    @FXML
    public DatePicker dateTimePicker;

    public TodoItem processResults(){
        String shortDescription=shortDesctiptionField.getText().trim();
        String details=detailsField.getText().trim();
        LocalDate deadLineValue=dateTimePicker.getValue();
       TodoItem item= new TodoItem(shortDescription,details,deadLineValue);
        ToDoData.getInstance().addTodoItem(item);
       return item;
    }

}
