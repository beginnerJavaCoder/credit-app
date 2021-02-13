package com.example.ui.component;

import com.example.model.Customer;
import com.example.service.CustomerService;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.KeyNotifier;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.validator.EmailValidator;
import com.vaadin.flow.data.validator.RegexpValidator;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;

@SpringComponent
@UIScope
public class CustomerEditor extends VerticalLayout implements KeyNotifier {

    private final CustomerService customerService;
    private Customer customer;

    private TextField surname = new TextField("Фамилия");
    private TextField firstName = new TextField("Имя");
    private TextField patronymic = new TextField("Отчество");
    private TextField phoneNumber = new TextField("Номер телефона");
    private EmailField email = new EmailField("Email");
    private TextField passport = new TextField("Паспорт");

    private Button save = new Button("Save", VaadinIcon.CHECK.create());
    private Button close = new Button("Cancel");
    private Button delete = new Button("", VaadinIcon.TRASH.create());
    private HorizontalLayout actions = new HorizontalLayout(save, close, delete);

    private Binder<Customer> binder = new Binder<>(Customer.class);
    private ChangeHandler changeHandler;

    @Autowired
    public CustomerEditor(CustomerService customerService) {
        this.customerService = customerService;

        surname.setWidthFull();
        firstName.setWidthFull();
        patronymic.setWidthFull();
        phoneNumber.setWidthFull();
        email.setWidthFull();
        passport.setWidthFull();
        save.setWidthFull();
        close.setWidthFull();
        actions.setWidthFull();

        add(surname, firstName, patronymic, phoneNumber, email, passport, actions);
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
        RegexpValidator permittedSymbols = new RegexpValidator(
                "Недопустимые символы",
                "[a-zA-Zа-яА-Я]+");

        binder.forField(surname)
                .withValidator(
                        text -> text.length() > 1,
                        "Фамилия слишком короткая")
                .withValidator(
                        text -> text.length() < 50,
                        "Фамилия слишком длинная")
                .withValidator(permittedSymbols)
                .asRequired()
                .bind(Customer::getSurname, Customer::setSurname);

        binder.forField(firstName)
                .withValidator(
                        text -> text.length() > 1,
                        "Имя слишком короткое")
                .withValidator(
                        text -> text.length() < 50,
                        "Имя слишком длинное")
                .withValidator(permittedSymbols)
                .asRequired()
                .bind(Customer::getFirstName, Customer::setFirstName);

        binder.forField(patronymic)
                .withValidator(
                        text -> text.length() > 1,
                        "Отчество слишком короткое")
                .withValidator(
                        text -> text.length() < 50,
                        "Отчество слишком длинное")
                .withValidator(permittedSymbols)
                .asRequired()
                .bind(Customer::getPatronymic, Customer::setPatronymic);

        binder.forField(phoneNumber)
                .withValidator(new RegexpValidator(
                        "Некорректный формат",
                        "\\+?\\d{11}"))
                .asRequired()
                .bind(Customer::getPhoneNumber, Customer::setPhoneNumber);

        binder.forField(email)
                .withValidator(new EmailValidator(
                        "Введите корректный email"))
                .asRequired()
                .bind(Customer::getEmail, Customer::setEmail);

        binder.forField(passport)
                .withValidator(
                        text -> text.length() == 10,
                        "Введите 10 цифр (серия+номер)")
                .withValidator(new RegexpValidator(
                        "Недопустимые символы",
                        "\\d{10}"))
                .asRequired()
                .bind(Customer::getPassport, Customer::setPassport);
    }

    public void editCustomer(Customer changedCustomer) {
        if (changedCustomer == null) {
            setVisible(false);
            return;
        }

        if (changedCustomer.getId() != null) {
            this.customer = customerService.findById(changedCustomer.getId());
            if (customer == null) {
                customer = changedCustomer;
            }
        } else {
            this.customer = changedCustomer;
        }
        delete.setVisible(customer.getId() != null);
        binder.setBean(customer);

        setVisible(true);

        surname.focus();
    }

    public void delete() {
        customerService.delete(customer);
        changeHandler.onChange();
    }

    public void save() {
        if (!binder.validate().hasErrors()) {
            customerService.save(customer);
            changeHandler.onChange();
        }
    }

    public void setChangeHandler(ChangeHandler changeHandler) {
        this.changeHandler = changeHandler;
    }
}
