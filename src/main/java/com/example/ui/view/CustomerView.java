package com.example.ui.view;

import com.example.ui.component.CustomerPanel;
import com.example.ui.layout.MainLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

@Route(value = "customers", layout = MainLayout.class)
@PageTitle("Credit App | Customers")
public class CustomerView extends VerticalLayout {

    private final CustomerPanel customerPanel;

    @Autowired
    public CustomerView(CustomerPanel customerPanel) {
        this.customerPanel = customerPanel;

        setSizeFull();

        this.customerPanel.initCustomerEditor();

        add(this.customerPanel);

        this.customerPanel.showCustomers("");
    }
}
