package se.yrgo.services.calls;

import org.springframework.transaction.annotation.Transactional;
import se.yrgo.domain.Action;
import se.yrgo.domain.Call;
import se.yrgo.services.customers.CustomerManagementMockImpl;
import se.yrgo.services.customers.CustomerManagementService;
import se.yrgo.services.customers.CustomerManagementServiceProductionImpl;
import se.yrgo.services.customers.CustomerNotFoundException;
import se.yrgo.services.diary.DiaryManagementService;
import se.yrgo.services.diary.DiaryManagementServiceImpl;
import se.yrgo.services.diary.DiaryManagementServiceMockImpl;


import java.util.Collection;

public class CallHandlingServiceImpl implements CallHandlingService {
    private CustomerManagementService customerService;
    private DiaryManagementService diary;

public CallHandlingServiceImpl(CustomerManagementService customerService, DiaryManagementService diary) {
    this.customerService = customerService;
    this.diary = diary;
}

    @Override
    @Transactional
    public void recordCall(String customerId, Call newCall, Collection<Action> actions) throws CustomerNotFoundException {
        customerService.recordCall(customerId, newCall);
        for (Action action : actions) {
            diary.recordAction(action);
        }
    }

    public void setCustomerService(CustomerManagementServiceProductionImpl customerService) {
        this.customerService = customerService;
    }

    public void setDiaryService(DiaryManagementServiceImpl diaryService) {
        this.diary = diaryService;
    }
}
