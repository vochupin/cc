package com.example.application.views.helloworld;

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

    private Button sayHello;
    private TextField textField;

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

        class Person {
            private String firstName;
            private String lastName;
            private String email;

            public Person(String firstName, String lastName, String email) {
                this.firstName = firstName;
                this.lastName = lastName;
                this.email = email;
            }

            public String getFirstName() { return firstName; }
            public String getLastName() { return lastName; }
            public String getEmail() { return email; }
        }

        // Create a Grid instance
        Grid<Person> grid = new Grid<>(Person.class, false); // 'false' prevents auto-creating columns

        // Add columns to the grid
        grid.addColumn(Person::getFirstName).setHeader("First Name");
        grid.addColumn(Person::getLastName).setHeader("Last Name");
        grid.addColumn(Person::getEmail).setHeader("Email Address");

        // Create some sample data
        List<Person> people = Arrays.asList(
                new Person("John", "Doe", "john.doe@example.com"),
                new Person("Jane", "Smith", "jane.smith@example.com"),
                new Person("Peter", "Jones", "peter.jones@example.com")
        );

        // Set the data items for the grid
        grid.setItems(people);

        grid.addItemClickListener(new ComponentEventListener<ItemClickEvent<Person>>() {
            @Override
            public void onComponentEvent(ItemClickEvent<Person> personItemClickEvent) {
                Notification.show("111");
            }
        });

        // Add the grid to the layout
        add(grid);    }

}
