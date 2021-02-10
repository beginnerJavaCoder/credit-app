package com.example.ui.view;

import com.example.model.Credit;
import com.example.model.Customer;
import com.example.ui.component.CreditPanel;
import com.example.ui.component.CustomerPanel;
import com.example.ui.layout.MainLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

@Route(value = "credit-arrangement", layout = MainLayout.class)
@PageTitle("Credit App | Credit Arrangement")
public class CreditArrangementView extends VerticalLayout {

    private final CustomerPanel customerPanel;
    private final CreditPanel creditPanel;


    private final Label chooseCustomerLabel;
    private final Label chooseCreditLabel;

    private final Button customerChoosingButton;
    private Button creditChoosingButton;

    private Customer chosenCustomer;
    private Credit chosenCredit;

    @Autowired
    public CreditArrangementView(CustomerPanel customerPanel, CreditPanel creditPanel) {
        this.customerPanel = customerPanel;
        this.creditPanel = creditPanel;

        chooseCustomerLabel = new Label("1) Выберите клиента из списка ");
        chooseCreditLabel = new Label("2) Выберите подходящий клиенту кредит");

        customerChoosingButton = new Button("Перейти к выбору кредита ->");
        customerChoosingButton.setWidth("20em");
        customerChoosingButton.setVisible(false);
        customerChoosingButton.addClickListener(e -> initStageTwo());

        HorizontalLayout upperPanel = customerPanel.getUpperPanel();
        upperPanel.add(chooseCustomerLabel, customerChoosingButton);

        setSizeFull();
        configureCustomerGrid();

        add(customerPanel);

        customerPanel.showCustomers("");
    }

    private void configureCustomerGrid() {
        customerPanel.getGrid()
                .asSingleSelect()
                .addValueChangeListener(e -> changeButtonState(e.getValue(), customerChoosingButton));
    }

    private void configureCreditGrid() {
        creditPanel.getGrid()
                .asSingleSelect()
                .addValueChangeListener(e -> changeCreditButtonState(e.getValue(), creditChoosingButton));
    }

    private void changeButtonState(Customer customer, Button button) {
        if (customer == null) {
            button.setVisible(false);
        } else {
            chosenCustomer = customer;
            button.setVisible(true);
        }
    }

    private void changeCreditButtonState(Credit credit, Button button) {
        if (credit == null) {
            button.setVisible(false);
        } else {
            chosenCredit = credit;
            button.setVisible(true);
        }
    }

    private void initStageTwo() {
        remove(customerPanel);

        configureCreditGrid();
        creditChoosingButton = new Button("Перейти к выбору параметров кредита ->");
        creditChoosingButton.setWidth("30em");
        creditChoosingButton.setVisible(false);
        creditChoosingButton.addClickListener(e -> initStageThree());
        HorizontalLayout upperPanel = creditPanel.getUpperPanel();
        upperPanel.add(chooseCreditLabel, creditChoosingButton);

        add(creditPanel);

        creditPanel.showCredits(null);
    }

    private void initStageThree() {
        remove(creditPanel);
    }
}
