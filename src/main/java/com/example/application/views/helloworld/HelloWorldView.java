package com.example.application.views.helloworld;

import com.example.application.data.entity.Client;
import com.example.application.data.service.ClientService;
import com.example.application.security.AuthenticatedUser;
import com.example.application.views.MainLayout;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.ItemClickEvent;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.server.VaadinSession;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;

import jakarta.annotation.security.PermitAll;

import java.util.Arrays;
import java.util.List;

@PageTitle("Hello World")
@Route(value = "hello", layout = MainLayout.class)
@RouteAlias(value = "", layout = MainLayout.class)
@PermitAll
public class HelloWorldView extends VerticalLayout {

    @Autowired
    AuthenticatedUser authenticatedUser;

    @Autowired
    ClientService clientService;

    private Button sayHello;
    private TextField textField;
    private Grid<Client> grid;

    public HelloWorldView() {

        /*
         * This trivial Vaadin session serializes just fine. To make testing
         * pros of JWT authentication, make it non-serializable. Object does
         * not serialize because of Java :-). This hack will make the session
         * lost on each server restart.
         */
        VaadinSession.getCurrent().setAttribute("foo", new Object());

        sayHello = new Button("Say hello!");
        sayHello.addClickListener(e -> {
            Notification.show("Hello " + authenticatedUser.get().get().getName());
        });

        textField = new TextField("Текст");


        add(sayHello);
        add(textField);

        // Create a Grid instance
        grid = new Grid<>(Client.class, false); // 'false' prevents auto-creating columns

        // Add columns to the grid
        grid.addColumn(Client::getFirstname).setHeader("First Name");
        grid.addColumn(Client::getLastname).setHeader("Last Name");

        grid.addItemClickListener(new ComponentEventListener<ItemClickEvent<Client>>() {
            @Override
            public void onComponentEvent(ItemClickEvent<Client> personItemClickEvent) {
                Notification.show("111");
            }
        });

        // Add the grid to the layout
        add(grid);    }

    @PostConstruct
    void fillData() {
        grid.setItems(clientService.all());
    }

}
