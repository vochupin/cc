package com.example.application.views.helloworld;

import com.example.application.data.entity.Client;
import com.example.application.data.service.ClientService;
import com.example.application.security.AuthenticatedUser;
import com.example.application.views.MainLayout;
import com.vaadin.flow.component.ComponentEventListener;
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

    private Button searchButton;
    private final TextField phoneTextField;
    private final TextField idCardTextField;
    private Grid<Client> grid;

    public DealsView() {

        /*
         * This trivial Vaadin session serializes just fine. To make testing
         * pros of JWT authentication, make it non-serializable. Object does
         * not serialize because of Java :-). This hack will make the session
         * lost on each server restart.
         */
        VaadinSession.getCurrent().setAttribute("foo", new Object());
        HorizontalLayout horizontalLayout = new HorizontalLayout();

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

        // Create a Grid instance
        grid = new Grid<>(Client.class, false); // 'false' prevents auto-creating columns

        // Add columns to the grid
        grid.addColumn(Client::getFirstname).setHeader("First Name");
        grid.addColumn(Client::getLastname).setHeader("Last Name");

        grid.addItemClickListener(new ComponentEventListener<ItemClickEvent<Client>>() {
            @Override
            public void onComponentEvent(ItemClickEvent<Client> personItemClickEvent) {
                Dialog dialog = new Dialog();

                dialog.setHeaderTitle("New employee");

                VerticalLayout dialogLayout = new VerticalLayout();
                dialog.add(dialogLayout);

                Button saveButton = new Button("Save");
                Button cancelButton = new Button("Cancel");
                dialog.getFooter().add(cancelButton);
                dialog.getFooter().add(saveButton);

                dialog.open();
            }
        });

        // Add the grid to the layout
        add(grid);
    }
}
