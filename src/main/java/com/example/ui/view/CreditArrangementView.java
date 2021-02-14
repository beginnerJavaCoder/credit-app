package com.example.ui.view;

import com.example.model.Credit;
import com.example.model.CreditOffer;
import com.example.model.Customer;
import com.example.model.Payment;
import com.example.service.CreditOfferService;
import com.example.ui.component.CreditDetailsPanel;
import com.example.ui.component.CreditPanel;
import com.example.ui.component.CustomerPanel;
import com.example.ui.layout.MainLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

@Route(value = "credit-arrangement", layout = MainLayout.class)
@PageTitle("Credit App | Credit Arrangement")
public class CreditArrangementView extends VerticalLayout {

    private final CustomerPanel customerPanel;
    private final CreditPanel creditPanel;
    private final CreditDetailsPanel creditDetailsPanel;

    private Button customerChoosingButton;
    private Button creditChoosingButton;

    private Customer chosenCustomer;
    private Credit chosenCredit;

    @Autowired
    public CreditArrangementView(CustomerPanel customerPanel,
                                 CreditPanel creditPanel,
                                 CreditDetailsPanel creditDetailsPanel) {

        this.customerPanel = customerPanel;
        this.creditPanel = creditPanel;
        this.creditDetailsPanel = creditDetailsPanel;

        setSizeFull();
        initChooseCustomerStage();
    }

    private void initChooseCustomerStage() {
        removeAll();

        configureCustomerChoosingButton();

        Label chooseCustomerLabel = new Label("1) Выберите клиента из списка ");
        customerPanel.getUpperPanel().add(chooseCustomerLabel, customerChoosingButton);

        configureCustomerGrid();

        add(customerPanel);

        customerPanel.showCustomers("");
    }

    private void configureCustomerChoosingButton() {
        customerChoosingButton = new Button("Перейти к выбору кредита ->");
        customerChoosingButton.setWidth("20em");
        customerChoosingButton.setVisible(false);
        customerChoosingButton.addClickListener(e -> initChooseCreditStage());
    }

    private void configureCustomerGrid() {
        customerPanel.getGrid()
                .asSingleSelect()
                .addValueChangeListener(e -> {
                    if (e.getValue() == null) {
                        customerChoosingButton.setVisible(false);
                    } else {
                        chosenCustomer = e.getValue();
                        customerChoosingButton.setVisible(true);
                    }
                });
    }

    private void initChooseCreditStage() {
        removeAll();

        configureCreditChoosingButton();

        Label chooseCreditLabel = new Label("2) Выберите подходящий клиенту кредит");
        creditPanel.getUpperPanel().add(chooseCreditLabel, creditChoosingButton);

        configureCreditGrid();

        add(creditPanel);

        creditPanel.showCredits(null);
    }

    private void configureCreditChoosingButton() {
        creditChoosingButton = new Button("Перейти к выбору параметров кредита ->");
        creditChoosingButton.setWidth("30em");
        creditChoosingButton.setVisible(false);
        creditChoosingButton.addClickListener(e -> initChooseCreditParamsStage());
    }

    private void configureCreditGrid() {
        creditPanel.getGrid()
                .asSingleSelect()
                .addValueChangeListener(e -> {
                    if (e.getValue() == null) {
                        creditChoosingButton.setVisible(false);
                    } else {
                        chosenCredit = e.getValue();
                        creditChoosingButton.setVisible(true);
                    }
                });
    }

    private void initChooseCreditParamsStage() {
        removeAll();

        creditDetailsPanel.setChosenCredit(chosenCredit);
        creditDetailsPanel.setChosenCustomer(chosenCustomer);

        add(creditDetailsPanel);
    }
}
