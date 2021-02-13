package com.example.ui.component;

import com.example.model.Credit;
import com.example.service.CreditService;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.KeyNotifier;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Objects;

@SpringComponent
@UIScope
public class CreditEditor extends VerticalLayout implements KeyNotifier {

    private final CreditService creditService;
    private Credit credit;

    private NumberField limit = new NumberField("Лимит по кредиту, ₽");
    private NumberField interestRate = new NumberField("Процентная ставка, %");

    private Button save = new Button("Save", VaadinIcon.CHECK.create());
    private Button close = new Button("Cancel");
    private Button delete = new Button("", VaadinIcon.TRASH.create());
    private HorizontalLayout actions = new HorizontalLayout(save, close, delete);

    private Binder<Credit> binder = new Binder<>(Credit.class);
    private ChangeHandler changeHandler;

    @Autowired
    public CreditEditor(CreditService creditService) {
        this.creditService = creditService;
        limit.setWidthFull();
        interestRate.setWidthFull();
        actions.setWidthFull();
        save.setWidthFull();
        close.setWidthFull();
        add(limit, interestRate,actions);
        initFieldsValidation();
        binder.bindInstanceFields(this);

        setSpacing(true);

        save.getElement().getThemeList().add("primary");
        delete.getElement().getThemeList().add("error");

        addKeyPressListener(Key.ENTER, e -> save());

        save.addClickListener(e -> save());
        delete.addClickListener(e -> delete());
        close.addClickListener(e -> changeHandler.onChange());
        setVisible(false);
    }

    private void initFieldsValidation() {
        binder.forField(limit)
                .withValidator(
                        Objects::nonNull,
                        "Введите значение")
                .withValidator(
                        number -> number >= 1000,
                        "Слишком маленькая сумма")
                .withValidator(
                        number -> number <= 100_000_000,
                        "Слишком большая сумма")
                .asRequired()
                .bind(Credit::getLimit, Credit::setLimit);

        binder.forField(interestRate)
                .withValidator(
                        Objects::nonNull,
                        "Введите значение")
                .withValidator(
                        number -> number >= 0.1 && number <= 100,
                        "Значение 0.1-100 %")
                .asRequired()
                .bind(Credit::getInterestRate, Credit::setInterestRate);
    }

    public void editCredit(Credit changedCredit) {
        if (changedCredit == null) {
            setVisible(false);
            return;
        }

        if (changedCredit.getId() != null) {
            this.credit = creditService.findById(changedCredit.getId());
            if (credit == null) {
                credit = changedCredit;
            }
        } else {
            this.credit = changedCredit;
        }
        delete.setVisible(credit.getId() != null);
        binder.setBean(credit);

        setVisible(true);

        limit.focus();
    }

    public void delete() {
        creditService.delete(credit);
        changeHandler.onChange();
    }

    public void save() {
        if (!binder.validate().hasErrors()) {
            creditService.save(credit);
            changeHandler.onChange();
        }
    }

    public void setChangeHandler(ChangeHandler changeHandler) {
        this.changeHandler = changeHandler;
    }
}
