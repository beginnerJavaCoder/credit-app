package com.example.view;

import com.example.component.CustomerEditor;
import com.example.model.Customer;
import com.example.repository.CustomerRepository;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

//TODO think about creation of class MainUI extends UI {...}
@Route
public class MainView extends VerticalLayout {

    private final CustomerRepository customerRepository;

    private final Grid<Customer> grid = new Grid<>(Customer.class);

    private final TextField filter = new TextField("", "type to filter");
    private final Button addNewButton = new Button("add new");
    private final HorizontalLayout horizontalLayout= new HorizontalLayout(filter, addNewButton);

    private final CustomerEditor editor;

    @Autowired
    public MainView(CustomerRepository customerRepository, CustomerEditor editor) {
        this.setSizeFull();
        this.customerRepository = customerRepository;
        this.editor = editor;

        add(horizontalLayout, grid, editor);
        grid.removeColumnByKey("entries");
        grid.removeColumnByKey("creditOffers");
        grid.setColumns("id", "surname", "firstName", "patronymic", "passport", "phoneNumber", "email");
        grid.getColumnByKey("surname").setHeader("Фамилия");
        grid.getColumnByKey("firstName").setHeader("Имя");
        grid.getColumnByKey("patronymic").setHeader("Отчество");
        grid.getColumnByKey("passport").setHeader("Паспорт");
        grid.getColumnByKey("phoneNumber").setHeader("Номер телефона");
        grid.getColumnByKey("email").setHeader("Email");

        grid.addThemeVariants(GridVariant.LUMO_COLUMN_BORDERS,
                GridVariant.LUMO_NO_ROW_BORDERS, GridVariant.LUMO_ROW_STRIPES);

        filter.setValueChangeMode(ValueChangeMode.EAGER);
        filter.addValueChangeListener(e -> listCustomers(e.getValue()));

        grid.asSingleSelect().addValueChangeListener(e -> {
            editor.editCustomer(e.getValue());
        });

        addNewButton.addClickListener(e -> editor.editCustomer(new Customer()));

        editor.setChangeHandler(() -> {
            editor.setVisible(false);
            listCustomers(filter.getValue());
        });

        listCustomers("");

    }

    private void listCustomers(String filterText) {
        if (filterText.isEmpty()) {
            grid.setItems(customerRepository.findAll());
        } else {
            grid.setItems(customerRepository.findBySurnameStartingWithIgnoreCase(filterText));
        }
    }
}
