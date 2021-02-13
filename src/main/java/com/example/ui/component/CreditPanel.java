package com.example.ui.component;

import com.example.model.Credit;
import com.example.service.CreditService;
import com.example.ui.util.GridThemeConfigurer;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

@SpringComponent
@UIScope
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class CreditPanel extends VerticalLayout {

    private final CreditService creditService;
    private final CreditEditor editor;

    private final Grid<Credit> grid;
    private final NumberField filter;
    private final HorizontalLayout upperPanel;
    private Button add;

    @Autowired
    public CreditPanel(CreditService creditService, CreditEditor editor) {
        this.creditService = creditService;
        this.editor = editor;

        grid = new Grid<>(Credit.class);
        filter = new NumberField("", "поиск по процентной ставке...");
        upperPanel = new HorizontalLayout(filter);

        setSizeFull();
        addClassName("credit-panel");
        configureGrid();
        configureFilter();
        configureUpperPanel();

        add(upperPanel, grid);
    }

    private void configureGrid() {
        GridThemeConfigurer.configureTheme(grid);

        grid.addClassName("credit-grid");
        grid.setSizeFull();
        grid.setColumns("limit", "interestRate");
        grid.getColumnByKey("limit").setHeader("Лимит по кредиту, ₽");
        grid.getColumnByKey("interestRate").setHeader("Процентная ставка, %");
    }

    private void configureFilter() {
        filter.setWidth("16em");
        filter.setValueChangeMode(ValueChangeMode.EAGER);
        filter.addValueChangeListener(e -> showCredits(e.getValue()));
    }

    private void configureUpperPanel() {
        upperPanel.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        upperPanel.setWidth("100%");
    }

    public void initCreditEditor() {
        configureEditor();
        updatePanel();
    }

    private void configureEditor() {
        editor.setChangeHandler(() -> {
            editor.setVisible(false);
            showCredits(filter.getValue());
        });
        editor.addClassName("credit-editor");
        add = new Button("Добавить кредит");
        add.addClickListener(e -> editor.editCredit(new Credit()));
        grid.asSingleSelect().addValueChangeListener(e -> editor.editCredit(e.getValue()));
    }

    private void updatePanel() {
        upperPanel.add(add);
        remove(grid);
        Div content = new Div(grid, editor);
        content.setSizeFull();
        content.setClassName("content");
        add(content);
    }

    public void showCredits(Double filterNumber) {
        if (filterNumber == null) {
            grid.setItems(creditService.findAll());
        } else {
            grid.setItems(creditService.findAll(filterNumber));
        }
    }

    public Grid<Credit> getGrid() {
        return grid;
    }

    public HorizontalLayout getUpperPanel() {
        return upperPanel;
    }
}
