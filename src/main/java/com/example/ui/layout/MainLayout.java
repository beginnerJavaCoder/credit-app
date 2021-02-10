package com.example.ui.layout;

import com.example.ui.view.CreditArrangementView;
import com.example.ui.view.CreditView;
import com.example.ui.view.CustomerView;
import com.example.ui.view.MainView;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.HighlightConditions;
import com.vaadin.flow.router.RouterLink;

import java.util.List;

@CssImport("./styles/shared-styles.css")
public class MainLayout extends AppLayout {

    public MainLayout() {
        createHeader();
        createDrawer();
    }

    private void createHeader() {
        H1 logo = new H1("Credit App");
        logo.addClassName("logo");

        HorizontalLayout header = new HorizontalLayout(new DrawerToggle(), logo);

        header.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        header.setWidth("100%");
        header.addClassName("header");

        addToNavbar(header);
    }

    private void createDrawer() {
        List<RouterLink> links = List.of(
                new RouterLink("Главная страница", MainView.class),
                new RouterLink("Клиенты банка", CustomerView.class),
                new RouterLink("Виды кредитов", CreditView.class),
                new RouterLink("Оформление кредита", CreditArrangementView.class)
        );

        VerticalLayout verticalLinks = new VerticalLayout();

        links.forEach(link -> {
            link.setHighlightCondition(HighlightConditions.sameLocation());
            verticalLinks.add(link);
        });

        addToDrawer(verticalLinks);
    }
}
