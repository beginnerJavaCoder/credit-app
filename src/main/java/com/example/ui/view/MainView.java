package com.example.ui.view;

import com.example.ui.layout.MainLayout;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import java.util.List;

@Route(value = "", layout = MainLayout.class)
@PageTitle("Credit App")
public class MainView extends VerticalLayout {

    public MainView() {
        List<H4> description = List.of(
                new H4("Данное приложение позволяет:"),
                new H4("1) Просматривать, удалять, редактировать существующих клиентов банка, а также создавать новых;"),
                new H4("2) Разрабатывать новые возможности кредитования;"),
                new H4("3) Оформлять на клиентов банка подходящие кредиты, с автоматическим расчетом необходимых параметров."),
                new H4(" "),
                new H4("Используйте навигационную панель для переходов по разделам приложения.")
                );

        description.forEach(this::add);
    }
}
