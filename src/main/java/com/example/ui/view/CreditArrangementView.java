package com.example.ui.view;

import com.example.model.Credit;
import com.example.model.CreditOffer;
import com.example.model.Customer;
import com.example.model.Payment;
import com.example.service.CreditOfferService;
import com.example.ui.component.CreditPanel;
import com.example.ui.component.CustomerPanel;
import com.example.ui.layout.MainLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
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

    private final CreditOfferService creditOfferService;


    private final Label chooseCustomerLabel;
    private final Label chooseCreditLabel;
    private Label chooseCreditParams;

    private final Button customerChoosingButton;
    private Button creditChoosingButton;
    private Button doCalculations;

    private NumberField creditSum;
    private NumberField creditTerm;

    private Customer chosenCustomer;
    private Credit chosenCredit;
    private CreditOffer creditOffer;

    @Autowired
    public CreditArrangementView(CustomerPanel customerPanel,
                                 CreditPanel creditPanel,
                                 CreditOfferService creditOfferService) {

        this.customerPanel = customerPanel;
        this.creditPanel = creditPanel;
        this.creditOfferService = creditOfferService;

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
        chooseCreditParams = new Label("3) Выберите срок кредита и сумму");
        creditSum = new NumberField();
        creditSum.setMin(1);
        creditSum.setMax(chosenCredit.getLimit());
        creditTerm = new NumberField();
        //TODO add field termLimit in credit table
        creditTerm.setMin(1);
        creditTerm.setMax(36);
        doCalculations = new Button("Перейти к расчетам параметров ->");
        doCalculations.setWidth("25em");
        doCalculations.addClickListener(e -> initStageFour());

        HorizontalLayout creditParamsPanel = new HorizontalLayout(creditSum, creditTerm, doCalculations);

        add(chooseCreditParams, creditParamsPanel);
    }

    private void initStageFour() {
        removeAll();
        creditOffer = new CreditOffer();
        creditOffer.setCredit(chosenCredit);
        creditOffer.setCreditAmount(creditSum.getValue());
        //TODO think about bidirectional relations (how to update it correctly?)
//        chosenCredit.getCreditOffers().add(creditOffer);
        creditOffer.setCustomer(chosenCustomer);
//        chosenCustomer.getCreditOffers().add(creditOffer);
        creditOffer.setPaymentSchedule(creditOfferService.calculatePaymentSchedule(creditOffer));
        Label l1 = new Label("Общая сумма" + creditOfferService.getTotalAmountOfCredit(creditOffer).toString());
        Label l2 = new Label("Сумма переплаты по процентам" + creditOfferService.getTotalAmountOfInterestRate(creditOffer).toString());
        Label l3 = new Label("Сумма ежемесячного платежа" + creditOfferService.getMonthlyPaymentAmount(creditOffer).toString());

        Grid<Payment> paymentGrid = new Grid<>(Payment.class);
        paymentGrid.setColumns("sum", "sumOfRepaymentForCreditPercents", "sumOfRepaymentForCreditBody");
        paymentGrid.getColumnByKey("sum").setHeader("Сумма платежа");
        paymentGrid.getColumnByKey("sumOfRepaymentForCreditPercents").setHeader("Процентная часть");
        paymentGrid.getColumnByKey("sumOfRepaymentForCreditBody").setHeader("Долговая часть");
        paymentGrid.setItems(creditOffer.getPaymentSchedule());
        Button arrangeCreditOffer = new Button("Оформить кредит");
        arrangeCreditOffer.setWidth("15em");
        arrangeCreditOffer.addClickListener(e -> {
            creditOfferService.save(creditOffer);
            removeAll();
            add(new Label("Кредит успешно оформлен!"));
        });

        add(l1, l2, l3, arrangeCreditOffer, paymentGrid);
    }
}
