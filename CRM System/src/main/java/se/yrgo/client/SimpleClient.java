package se.yrgo.client;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import se.yrgo.domain.Action;
import se.yrgo.domain.Call;
import se.yrgo.domain.Customer;
import se.yrgo.services.calls.CallHandlingService;
import se.yrgo.services.customers.CustomerManagementService;
import se.yrgo.services.customers.CustomerNotFoundException;
import se.yrgo.services.diary.DiaryManagementService;

import java.util.*;


public class SimpleClient {

    public static void main(String[] args) {

        try (ClassPathXmlApplicationContext container = new ClassPathXmlApplicationContext("application.xml")) {

            CustomerManagementService customerService = container.getBean(CustomerManagementService.class);
            CallHandlingService callService = container.getBean(CallHandlingService.class);
            DiaryManagementService diaryService = container.getBean(DiaryManagementService.class);

            customerService.newCustomer(new Customer("AB-12", "Diamond Divider", "Do not mention blood diamonds"));

            Call newCall = new Call("Pearl Carlson called about next weeks meeting, was very upset about blood diamonds being on the agenda!!");
            Action firstAction = new Action("Call back and apologize, blame the intern", new GregorianCalendar(2026, Calendar.MAY, 20), "EJ");
            Action secondAction = new Action("Make sure Diamond Divider gets a discount on their next order", new GregorianCalendar(2026, Calendar.JUNE, 1), "EJ");

            List<Action> actions = new ArrayList<Action>();
            actions.add(firstAction);
            actions.add(secondAction);

            try {
                callService.recordCall("AB-12", newCall, actions);
            } catch (CustomerNotFoundException e) {
                System.out.println("Customer not found.");
            }

            System.out.println("To do: ");
            Collection<Action> incompleteActions = diaryService.getAllIncompleteActions("EJ");
            for (Action action : incompleteActions) {
                System.out.println(action);
            }
        }

    }
}