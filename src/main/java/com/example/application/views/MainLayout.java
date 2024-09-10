package com.example.application.views;

import com.example.application.views.cliente.ClienteView;
import com.example.application.views.mecanico.MecanicoView;
import com.example.application.views.os.OSView;
import com.example.application.views.peças.PeçasView;
import com.example.application.views.serviços.ServiçosView;
import com.example.application.views.veiculo.VeiculoView;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Footer;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Header;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.sidenav.SideNav;
import com.vaadin.flow.component.sidenav.SideNavItem;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.theme.lumo.LumoUtility;
import model.User;

public class MainLayout extends AppLayout implements BeforeEnterObserver {

    private H1 viewTitle;

    public MainLayout() {
        setPrimarySection(Section.DRAWER);
        addDrawerContent();
        addHeaderContent();
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        User currentUser = (User) UI.getCurrent().getSession().getAttribute(User.class);
        if (currentUser == null) {
            event.forwardTo("login");
        }
    }

    private void addHeaderContent() {
        DrawerToggle toggle = new DrawerToggle();
        toggle.setAriaLabel("Menu toggle");

        viewTitle = new H1();
        viewTitle.addClassNames(LumoUtility.FontSize.LARGE, LumoUtility.Margin.NONE);

        HorizontalLayout headerLayout = new HorizontalLayout(toggle, viewTitle);
        headerLayout.setDefaultVerticalComponentAlignment(Alignment.CENTER);
        headerLayout.expand(viewTitle);
        headerLayout.setWidthFull();

        addToNavbar(true, headerLayout);
    }

    private void addDrawerContent() {
        Span appName = new Span("Menu");
        appName.addClassNames(LumoUtility.FontWeight.SEMIBOLD, LumoUtility.FontSize.LARGE);
        Header header = new Header(appName);

        SideNav sideNav = createNavigation();
    
        Scroller scroller = new Scroller(sideNav);
    
        Button logoutButton = new Button("Logout", VaadinIcon.EXIT.create(), event -> {

            UI.getCurrent().getSession().setAttribute(User.class, null);
            UI.getCurrent().navigate("login");
        });
        logoutButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        logoutButton.getStyle().set("width", "100%"); 

        VerticalLayout footer = new VerticalLayout(logoutButton);
        footer.setPadding(false);
        footer.setSpacing(false);
        footer.setWidthFull();
        footer.setJustifyContentMode(FlexComponent.JustifyContentMode.END);
    
        VerticalLayout drawerContent = new VerticalLayout(header, scroller, footer);
        drawerContent.setSpacing(true); 
        drawerContent.setPadding(true); 
        drawerContent.setSizeFull(); 
        drawerContent.setAlignItems(FlexComponent.Alignment.STRETCH); 

        drawerContent.setFlexGrow(1, scroller);
    
        addToDrawer(drawerContent);
    }
    


    private SideNav createNavigation() {
        SideNav nav = new SideNav();

        nav.addItem(new SideNavItem("Cliente", ClienteView.class, VaadinIcon.GROUP.create()));
        nav.addItem(new SideNavItem("Mecânico", MecanicoView.class, VaadinIcon.MALE.create()));
        nav.addItem(new SideNavItem("Veículo", VeiculoView.class, VaadinIcon.CAR.create()));
        nav.addItem(new SideNavItem("Peças", PeçasView.class, VaadinIcon.COG.create()));
        nav.addItem(new SideNavItem("Serviços", ServiçosView.class, VaadinIcon.HANDSHAKE.create()));
        nav.addItem(new SideNavItem("OS", OSView.class, VaadinIcon.FILE_TEXT.create()));

        return nav;
    }

    private Footer createFooter() {
        Footer layout = new Footer();

        return layout;
    }

    @Override
    protected void afterNavigation() {
        super.afterNavigation();
        viewTitle.setText(getCurrentPageTitle());
    }

    private String getCurrentPageTitle() {
        PageTitle title = getContent().getClass().getAnnotation(PageTitle.class);
        return title == null ? "" : title.value();
    }
}
