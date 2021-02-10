package com.example.ui.view;

import com.example.ui.layout.MainLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "credit-arrangement", layout = MainLayout.class)
@PageTitle("Credit App | Credit Arrangement")
public class CreditArrangementView extends VerticalLayout {

    public CreditArrangementView() {

    }
}
