package com.example.ui.view;

import com.example.ui.component.CreditPanel;
import com.example.ui.layout.MainLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

@Route(value = "credits", layout = MainLayout.class)
@PageTitle("Credit App | Credits")
public class CreditView extends VerticalLayout {

    private final CreditPanel creditPanel;

    @Autowired
    public CreditView(CreditPanel creditPanel) {
        this.creditPanel = creditPanel;

        setSizeFull();

        this.creditPanel.initCreditEditor();

        add(this.creditPanel);

        this.creditPanel.showCredits(null);
    }
}
