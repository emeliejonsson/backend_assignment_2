package se.yrgo.services.diary;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.yrgo.dataaccess.ActionDao;
import se.yrgo.domain.Action;


import java.util.List;
@Service
public class DiaryManagementServiceImpl implements DiaryManagementService {
    private ActionDao actionDao;

    public DiaryManagementServiceImpl(ActionDao actionDao) {
        this.actionDao = actionDao;
    }

    @Override
    @Transactional
    public void recordAction(Action action) {
        actionDao.create(action);

    }

    @Override
    public List<Action> getAllIncompleteActions(String requiredUser) {
        return actionDao.getIncompleteActions(requiredUser);
    }
}
