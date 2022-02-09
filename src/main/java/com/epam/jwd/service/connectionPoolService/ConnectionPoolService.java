package com.epam.jwd.service.connectionPoolService;

import com.epam.jwd.dao.api.ConnectionPool;
import com.epam.jwd.dao.exception.DaoException;
import com.epam.jwd.dao.impl.connectionPool.ConnectionPoolImpl;
import com.epam.jwd.service.exception.ServiceException;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.Objects;

/**
 * Class which initialize connection pool {@link ConnectionPool}
 */
public class ConnectionPoolService {
    private static final Logger logger = LogManager.getLogger(ConnectionPoolService.class);

    private static final String CAN_NOT_INITIALIZE_CONNECTION_POOL_EXCEPTION = "Can not initialize connection pool";
    private static final String CAN_NOT_SHUTDOWN_CONNECTION_POOL_EXCEPTION = "Can not shutdown connection pool";
    private static ConnectionPoolService instance = new ConnectionPoolService();
    private final ConnectionPool pool = ConnectionPoolImpl.getInstance();

    private ConnectionPoolService() {

    }

    public static ConnectionPoolService getInstance() {
        if (Objects.isNull(instance)) {
            instance = new ConnectionPoolService();
        }
        return instance;
    }

    /**
     * Method for initializing connectionPool
     *
     * @throws ServiceException - if it's unable for create connection
     */
    public void initialize() throws ServiceException {
        try {
            pool.initialize();
        } catch (DaoException e) {
            logger.error(CAN_NOT_INITIALIZE_CONNECTION_POOL_EXCEPTION + e);
            throw new ServiceException(CAN_NOT_INITIALIZE_CONNECTION_POOL_EXCEPTION);
        }
    }

    /**
     * Method which shutDowns connection poll
     *
     * @throws ServiceException - if it's unable for close connection
     */
    public void shutDown() throws ServiceException {
        try {
            pool.shutdown();
        } catch (DaoException e) {
            logger.error(CAN_NOT_SHUTDOWN_CONNECTION_POOL_EXCEPTION + e);
            throw new ServiceException(CAN_NOT_SHUTDOWN_CONNECTION_POOL_EXCEPTION);
        }
    }
}
