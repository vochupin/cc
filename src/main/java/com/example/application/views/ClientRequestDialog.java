package com.example.application.views;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class ClientRequestDialog extends Dialog {
    public ClientRequestDialog() {
        setHeaderTitle("Оформление обращения");
        setWidth(800, Unit.PIXELS);
        setHeight(600, Unit.PIXELS);

        VerticalLayout dialogLayout = new VerticalLayout();
        add(dialogLayout);
        dialogLayout.add(new Paragraph("Тут надо навалить контролов для оформления обращения: то есть выбор темы, заполнение комментария"));

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
