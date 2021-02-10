package com.example.ui.component;

import com.example.model.Customer;
import com.example.repository.CustomerRepository;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.KeyNotifier;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;

@SpringComponent
@UIScope
public class CustomerEditor extends VerticalLayout implements KeyNotifier {

    private final CustomerRepository customerRepository;
    private Customer customer;

    private TextField surname = new TextField("Фамилия");
    private TextField firstName = new TextField("Имя");
    private TextField patronymic = new TextField("Отчество");
    private TextField phoneNumber = new TextField("Номер телефона");
    private TextField email = new TextField("Email");
    private TextField passport = new TextField("Паспорт");

    private Button save = new Button("Save", VaadinIcon.CHECK.create());
    private Button cancel = new Button("Cancel");
    private Button delete = new Button("Delete", VaadinIcon.TRASH.create());
    private HorizontalLayout actions = new HorizontalLayout(save, cancel, delete);

    private Binder<Customer> binder = new Binder<>(Customer.class);
    private ChangeHandler changeHandler;

    public interface ChangeHandler {
        void onChange();
    }

    @Autowired
    public CustomerEditor(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;

        add(surname, firstName, patronymic, phoneNumber, email, passport, actions);

        binder.bindInstanceFields(this);

        setSpacing(true);

        save.getElement().getThemeList().add("primary");
        delete.getElement().getThemeList().add("error");

        addKeyPressListener(Key.ENTER, e -> save());

        save.addClickListener(e -> save());
        delete.addClickListener(e -> delete());
        cancel.addClickListener(e -> editCustomer(customer));
        setVisible(false);
    }

    public void editCustomer(Customer changedCustomer) {
        if (changedCustomer == null) {
            setVisible(false);
            return;
        }

        if (changedCustomer.getId() != null) {
            this.customer = customerRepository.findById(changedCustomer.getId()).orElse(changedCustomer);
        } else {
            this.customer = changedCustomer;
        }
        cancel.setVisible(customer.getId() != null);
        binder.setBean(customer);

        setVisible(true);

        surname.focus();
    }

    public void delete() {
        customerRepository.delete(customer);
        changeHandler.onChange();
    }

    public void save() {
        customerRepository.save(customer);
        changeHandler.onChange();
    }

    public void setChangeHandler(ChangeHandler changeHandler) {
        this.changeHandler = changeHandler;
    }
}
