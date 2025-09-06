package com.example.application.views;

import com.example.application.data.entity.Client;
import com.example.application.data.service.ClientService;
import com.example.application.security.AuthenticatedUser;
import com.example.application.views.MainLayout;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.ItemClickEvent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.server.VaadinSession;
import org.springframework.beans.factory.annotation.Autowired;

import jakarta.annotation.security.PermitAll;

@PageTitle("Договоры")
@Route(value = "deals", layout = MainLayout.class)
@RouteAlias(value = "", layout = MainLayout.class)
@PermitAll
public class DealsView extends VerticalLayout {

    @Autowired
    AuthenticatedUser authenticatedUser;

    @Autowired
    ClientService clientService;

    private Button clientRequestButton;
    private Button searchButton;
    private final TextField phoneTextField;
    private final TextField idCardTextField;
    private Grid<Client> grid;

    private Dialog dialog;

    public DealsView() {

        /*
         * This trivial Vaadin session serializes just fine. To make testing
         * pros of JWT authentication, make it non-serializable. Object does
         * not serialize because of Java :-). This hack will make the session
         * lost on each server restart.
         */
        VaadinSession.getCurrent().setAttribute("foo", new Object());
        HorizontalLayout horizontalLayout = new HorizontalLayout();

        clientRequestButton = new Button("Создать обращение");
        clientRequestButton.addClickListener(new ComponentEventListener<ClickEvent<Button>>() {
            @Override
            public void onComponentEvent(ClickEvent<Button> buttonClickEvent) {
                if (searchButton.isEnabled()) {
                    clientRequestButton.setText("Создать обращение");
                    new ClientRequestDialog().open();
                } else {
                    clientRequestButton.setText("Оформить обращение");
                }
                toggleEnabled();
            }
        });
        add(clientRequestButton);

        searchButton = new Button("Найти");
        searchButton.addClickListener(e -> {
            grid.setItems(clientService.all());
        });

        phoneTextField = new TextField("Телефон");
        idCardTextField = new TextField("ID card");

        horizontalLayout.add(phoneTextField);
        horizontalLayout.add(idCardTextField);

        add(horizontalLayout);
        add(searchButton);

        disableControls();

        // Create a Grid instance
        grid = new Grid<>(Client.class, false); // 'false' prevents auto-creating columns

        grid.setHeight(600, Unit.PIXELS);

        // Add columns to the grid
        grid.addColumn(Client::getFirstname).setHeader("Имя");
        grid.addColumn(Client::getLastname).setHeader("Фамилия");
        grid.addColumn(Client::getPhone).setHeader("Телефон");

        grid.addItemClickListener(new ComponentEventListener<ItemClickEvent<Client>>() {
            @Override
            public void onComponentEvent(ItemClickEvent<Client> personItemClickEvent) {
                new DealDialog().open();
            }
        });

        // Add the grid to the layout
        add(grid);
    }

    private void disableControls() {
        searchButton.setEnabled(false);
        phoneTextField.setEnabled(false);
        idCardTextField.setEnabled(false);
    }

    private void toggleEnabled() {
        if (searchButton.isEnabled()) {
            searchButton.setEnabled(false);
            phoneTextField.setEnabled(false);
            idCardTextField.setEnabled(false);
        } else {
            searchButton.setEnabled(true);
            phoneTextField.setEnabled(true);
            idCardTextField.setEnabled(true);
        }
    }
}
