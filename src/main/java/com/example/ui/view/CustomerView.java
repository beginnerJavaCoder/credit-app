package com.example.ui.view;

import com.example.ui.util.GridThemeConfigurer;
import com.example.ui.component.CustomerEditor;
import com.example.model.Customer;
import com.example.repository.CustomerRepository;
import com.example.ui.layout.MainLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

@Route(value = "customers", layout = MainLayout.class)
@PageTitle("Credit App | Customers")
public class CustomerView extends VerticalLayout {

    private final CustomerRepository customerRepository;

    private final Grid<Customer> grid;
    private final CustomerEditor editor;
    private final TextField filter;
    private final Button addNewCustomerButton;

    @Autowired
    public CustomerView(CustomerRepository customerRepository, CustomerEditor editor) {
        this.customerRepository = customerRepository;
        this.editor = editor;
        grid = new Grid<>(Customer.class);
        filter = new TextField("", "поиск по фамилии...");
        addNewCustomerButton = new Button("Добавить клиента");
        HorizontalLayout upperPanel = new HorizontalLayout(filter, addNewCustomerButton);

        setSizeFull();
        configureGrid();
        configureFilter();
        configureAddNewCustomerButton();
        configureEditor();

        add(upperPanel, grid, this.editor);

        showCustomers("");
    }

    private void configureGrid() {
        GridThemeConfigurer.configureTheme(grid);

        grid.setColumns("id", "surname", "firstName", "patronymic", "passport", "phoneNumber", "email");
        grid.getColumnByKey("surname").setHeader("Фамилия");
        grid.getColumnByKey("firstName").setHeader("Имя");
        grid.getColumnByKey("patronymic").setHeader("Отчество");
        grid.getColumnByKey("passport").setHeader("Паспорт");
        grid.getColumnByKey("phoneNumber").setHeader("Номер телефона");
        grid.getColumnByKey("email").setHeader("Email");

        grid.asSingleSelect().addValueChangeListener(e -> editor.editCustomer(e.getValue()));
    }

    private void configureFilter() {
        filter.setValueChangeMode(ValueChangeMode.EAGER);
        filter.addValueChangeListener(e -> showCustomers(e.getValue()));
    }

    private void configureAddNewCustomerButton() {
        addNewCustomerButton.addClickListener(e -> editor.editCustomer(new Customer()));
    }

    private void configureEditor() {
        editor.setChangeHandler(() -> {
            editor.setVisible(false);
            showCustomers(filter.getValue());
        });
    }

    private void showCustomers(String filterText) {
        if (filterText.isEmpty()) {
            grid.setItems(customerRepository.findAll());
        } else {
            grid.setItems(customerRepository.findBySurnameStartingWithIgnoreCase(filterText));
        }
    }
}
