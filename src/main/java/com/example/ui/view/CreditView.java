package com.example.ui.view;

import com.example.model.Credit;
import com.example.repository.CreditRepository;
import com.example.ui.util.GridThemeConfigurer;
import com.example.ui.layout.MainLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

@Route(value = "credits", layout = MainLayout.class)
@PageTitle("Credit App | Credits")
public class CreditView extends VerticalLayout {

    private final CreditRepository creditRepository;
    private final Grid<Credit> grid;
    private final TextField filter;

    @Autowired
    public CreditView(CreditRepository creditRepository) {
        this.creditRepository = creditRepository;
        grid = new Grid<>(Credit.class);
        filter = new TextField("", "поиск по процентной ставке...");

        setSizeFull();
        configureGrid();
        configureFilter();
        add(filter, grid);

        showCredits("");
    }

    private void configureGrid() {
        GridThemeConfigurer.configureTheme(grid);

        grid.setColumns("id", "limit", "interestRate");
        grid.getColumnByKey("limit").setHeader("Лимит по кредиту, ₽");
        grid.getColumnByKey("interestRate").setHeader("Процентная ставка, %");
    }

    private void configureFilter() {
        filter.setWidth("16em");
        filter.setPreventInvalidInput(true);
        filter.setValueChangeMode(ValueChangeMode.EAGER);
        filter.addValueChangeListener(e -> showCredits(e.getValue()));
    }

    private void showCredits(String filterText) {
        if (filterText.isEmpty()) {
            grid.setItems(creditRepository.findAll());
        } else {
            try {
                grid.setItems(creditRepository.findByInterestRate(Double.parseDouble(filterText)));
            } catch (NumberFormatException e) {
                grid.setItems();
            }
        }
    }
}
