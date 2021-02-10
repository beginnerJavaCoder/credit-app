package com.example.ui.component;

import com.example.model.Credit;
import com.example.repository.CreditRepository;
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

@SpringComponent
@UIScope
public class CreditEditor extends VerticalLayout implements KeyNotifier {

    private final CreditRepository creditRepository;
    private Credit credit;

    private NumberField limit = new NumberField("Лимит по кредиту, ₽");
    private NumberField interestRate = new NumberField("Процентная ставка, %");

    private Button save = new Button("Save", VaadinIcon.CHECK.create());
    private Button cancel = new Button("Cancel");
    private Button delete = new Button("Delete", VaadinIcon.TRASH.create());
    private HorizontalLayout actions = new HorizontalLayout(save, cancel, delete);

    private Binder<Credit> binder = new Binder<>(Credit.class);
    private ChangeHandler changeHandler;

    @Autowired
    public CreditEditor(CreditRepository creditRepository) {
        this.creditRepository = creditRepository;
        limit.setWidth("10em");
        interestRate.setWidth("10em");
        add(limit, interestRate,actions);

        binder.bindInstanceFields(this);

        setSpacing(true);

        save.getElement().getThemeList().add("primary");
        delete.getElement().getThemeList().add("error");

        addKeyPressListener(Key.ENTER, e -> save());

        save.addClickListener(e -> save());
        delete.addClickListener(e -> delete());
        cancel.addClickListener(e -> editCredit(credit));
        setVisible(false);
    }

    public void editCredit(Credit changedCredit) {
        if (changedCredit == null) {
            setVisible(false);
            return;
        }

        if (changedCredit.getId() != null) {
            this.credit = creditRepository.findById(changedCredit.getId()).orElse(changedCredit);
        } else {
            this.credit = changedCredit;
        }
        cancel.setVisible(credit.getId() != null);
        binder.setBean(credit);

        setVisible(true);

        limit.focus();
    }

    public void delete() {
        creditRepository.delete(credit);
        changeHandler.onChange();
    }

    public void save() {
        creditRepository.save(credit);
        changeHandler.onChange();
    }

    public void setChangeHandler(ChangeHandler changeHandler) {
        this.changeHandler = changeHandler;
    }
}
