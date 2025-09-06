package com.example.application.views;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class DealDialog extends Dialog {
    public DealDialog() {
        setHeaderTitle("Данные договора");
        setWidth(800, Unit.PIXELS);
        setHeight(600, Unit.PIXELS);

        VerticalLayout dialogLayout = new VerticalLayout();
        add(dialogLayout);

        Button closeButton = new Button("Закрыть");
        closeButton.addClickListener(new ComponentEventListener<ClickEvent<Button>>() {
            @Override
            public void onComponentEvent(ClickEvent<Button> buttonClickEvent) {
                close();
            }
        });
        getFooter().add(closeButton);
    }
}
