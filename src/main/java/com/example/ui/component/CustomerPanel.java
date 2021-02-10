package com.example.ui.component;

import com.example.model.Customer;
import com.example.repository.CustomerRepository;
import com.example.ui.util.GridThemeConfigurer;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

@SpringComponent
@UIScope
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class CustomerPanel extends VerticalLayout {

    private final CustomerRepository customerRepository;
    private final CustomerEditor editor;

    private final Grid<Customer> grid;
    private final TextField filter;
    private final HorizontalLayout upperPanel;
    private Button add;

    @Autowired
    public CustomerPanel(CustomerRepository customerRepository, CustomerEditor editor) {
        this.customerRepository = customerRepository;
        this.editor = editor;

        grid = new Grid<>(Customer.class);
        filter = new TextField("", "поиск по фамилии...");
        upperPanel = new HorizontalLayout(filter);

        setSizeFull();
        configureGrid();
        configureFilter();
        configureUpperPanel();

        add(upperPanel, grid);
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
    }

    private void configureFilter() {
        filter.setValueChangeMode(ValueChangeMode.EAGER);
        filter.addValueChangeListener(e -> showCustomers(e.getValue()));
    }

    private void configureUpperPanel() {
        upperPanel.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        upperPanel.setWidth("100%");
    }

    public void initCustomerEditor() {
        configureEditor();
        updatePanel();
    }

    private void configureEditor() {
        editor.setChangeHandler(() -> {
            editor.setVisible(false);
            showCustomers(filter.getValue());
        });
        add = new Button("Добавить клиента");
        add.addClickListener(e -> editor.editCustomer(new Customer()));
        grid.asSingleSelect().addValueChangeListener(e -> editor.editCustomer(e.getValue()));
    }

    private void updatePanel() {
        upperPanel.add(add);
        add(editor);
    }

    public void showCustomers(String filterText) {
        if (filterText.isEmpty()) {
            grid.setItems(customerRepository.findAll());
        } else {
            grid.setItems(customerRepository.findBySurnameStartingWithIgnoreCase(filterText));
        }
    }

    public Grid<Customer> getGrid() {
        return grid;
    }

    public HorizontalLayout getUpperPanel() {
        return upperPanel;
    }
}
