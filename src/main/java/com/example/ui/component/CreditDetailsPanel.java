package com.example.ui.component;

import com.example.model.Credit;
import com.example.model.CreditOffer;
import com.example.model.Customer;
import com.example.model.Payment;
import com.example.service.CreditOfferService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

@SpringComponent
@UIScope
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class CreditDetailsPanel extends VerticalLayout {

    private final CreditOfferService creditOfferService;

    private Customer chosenCustomer;
    private Credit chosenCredit;
    private CreditOffer creditOffer;

    private final Grid<Payment> paymentGrid;
    private HorizontalLayout upperPanel;
    private VerticalLayout upperGridPanel;
    private Button calculateButton;
    private Button arrangeCreditOfferButton;
    private NumberField creditSum;
    private NumberField creditTerm;

    @Autowired
    public CreditDetailsPanel(CreditOfferService creditOfferService) {
        this.creditOfferService = creditOfferService;
        paymentGrid = new Grid<>(Payment.class);
        upperGridPanel = new VerticalLayout();
        upperPanel = new HorizontalLayout();

        setSizeFull();
        configureGrid();

        add(new Label("3) Выберите срок кредита и сумму"), upperPanel, upperGridPanel, paymentGrid);
    }

    private void configureUpperPanel() {
        creditSum = new NumberField("Сумма кредита, ₽");
        creditSum.setMin(1000);
        creditSum.setMax(chosenCredit.getLimit());
        creditTerm = new NumberField("Срок кредита, мес.");
        creditTerm.setMin(12);
        creditTerm.setMax(60);

        configureCalculateButton();

        upperPanel.add(creditSum, creditTerm, calculateButton);
        upperPanel.setDefaultVerticalComponentAlignment(Alignment.BASELINE);
    }

    private void configureCalculateButton() {
        calculateButton = new Button("Рассчитать график платежей");
        calculateButton.setWidth("20em");
        calculateButton.addClickListener(e -> {
            if (isValid()) {
                calculateCreditParams();
            }
        });
    }

    private boolean isValid() {
        if (creditSum.isInvalid()) {
            return false;
        }
        if (creditTerm.isInvalid()) {
            return false;
        }
        Double tmp = creditTerm.getValue();
        if (tmp % tmp.intValue() != 0) {
            return false;
        }

        return true;
    }

    private void calculateCreditParams() {
        initCreditOffer();
        configureUpperGridPanel();
        paymentGrid.setItems(creditOffer.getPaymentSchedule());
        paymentGrid.setVisible(true);
    }

    private void configureUpperGridPanel() {
        Label l1 = new Label("Общая сумма: " + creditOfferService.getTotalAmountOfCredit(creditOffer).toString());
        Label l2 = new Label("Сумма переплаты по процентам: " + creditOfferService.getTotalAmountOfInterestRate(creditOffer).toString());
        Label l3 = new Label("Сумма ежемесячного платежа: " + creditOfferService.getMonthlyPaymentAmount(creditOffer).toString());

        configureArrangeCreditOfferButton();
        upperGridPanel.removeAll();
        upperGridPanel.add(l1, l2, l3, arrangeCreditOfferButton);
    }

    private void configureArrangeCreditOfferButton() {
        arrangeCreditOfferButton = new Button("Оформить кредит");
        arrangeCreditOfferButton.setWidth("15em");
        arrangeCreditOfferButton.addClickListener(e -> {
            creditOfferService.save(creditOffer);
            this.removeAll();
            this.add(new H4("Кредит успешно оформлен!"));
        });
    }

    private void configureGrid() {
        paymentGrid.setSizeFull();
        paymentGrid.setColumns("sum", "sumOfRepaymentForCreditPercents", "sumOfRepaymentForCreditBody");
        paymentGrid.getColumnByKey("sum").setHeader("Сумма платежа");
        paymentGrid.getColumnByKey("sumOfRepaymentForCreditPercents").setHeader("Процентная часть");
        paymentGrid.getColumnByKey("sumOfRepaymentForCreditBody").setHeader("Долговая часть");
        paymentGrid.setVisible(false);
    }

    private void initCreditOffer() {
        creditOffer = new CreditOffer();
        creditOffer.setCredit(chosenCredit);
        creditOffer.setCreditAmount(creditSum.getValue());
        creditOffer.setCustomer(chosenCustomer);
        creditOffer.setPaymentSchedule(creditOfferService.calculatePaymentSchedule(creditOffer));
    }

    public void setChosenCustomer(Customer chosenCustomer) {
        this.chosenCustomer = chosenCustomer;
    }

    public void setChosenCredit(Credit chosenCredit) {
        this.chosenCredit = chosenCredit;
        configureUpperPanel();
    }

    public HorizontalLayout getUpperPanel() {
        return upperPanel;
    }
}
