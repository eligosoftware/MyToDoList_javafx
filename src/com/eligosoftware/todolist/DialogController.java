package com.eligosoftware.todolist;

import com.eligosoftware.todolist.dataModel.ToDoData;
import com.eligosoftware.todolist.dataModel.TodoItem;
import com.sun.xml.internal.bind.v2.TODO;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.time.LocalDate;

public class DialogController {

    private TodoItem item;

    @FXML
    public TextField shortDesctiptionField;

    @FXML
    public TextArea detailsField;

    @FXML
    public DatePicker dateTimePicker;


    public void setItem(TodoItem item) {
        this.item = item;
        shortDesctiptionField.setText(item.getShortDescription());
        detailsField.setText(item.getDetails());
        dateTimePicker.setValue(item.getDeadline());
    }

    public TodoItem processResults(){
        String shortDescription=shortDesctiptionField.getText().trim();
        String details=detailsField.getText().trim();
        LocalDate deadLineValue=dateTimePicker.getValue();
       TodoItem item= new TodoItem(shortDescription,details,deadLineValue);
        ToDoData.getInstance().addTodoItem(item);
       return item;
    }
    public void updateItem(){
        String shortDescription=shortDesctiptionField.getText().trim();
        String details=detailsField.getText().trim();
        LocalDate deadLineValue=dateTimePicker.getValue();

        item.setShortDescription(shortDescription);
        item.setDeadline(deadLineValue);
        item.setDetails(details);
    }

}
