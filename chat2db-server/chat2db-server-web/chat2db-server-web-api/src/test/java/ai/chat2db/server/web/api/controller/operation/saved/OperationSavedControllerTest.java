package ai.chat2db.server.web.api.controller.operation.saved;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.*;

import ai.chat2db.server.domain.api.param.operation.OperationSavedParam;
import ai.chat2db.server.web.api.controller.operation.saved.request.OperationCreateRequest;
import ai.chat2db.server.domain.api.service.OperationService;
import ai.chat2db.server.web.api.controller.operation.saved.converter.OperationWebConverter;
import ai.chat2db.server.tools.base.wrapper.result.DataResult;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class OperationSavedControllerTest {

    @Mock
    private OperationService operationService;

    @Mock
    private OperationWebConverter operationWebConverter;

    @InjectMocks
    private OperationSavedController operationSavedController;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreate() {

        OperationCreateRequest request = new OperationCreateRequest();

        // set mock param, so that req2param won't return null
        OperationSavedParam mockParam = new OperationSavedParam();
        when(operationWebConverter.req2param(any(OperationCreateRequest.class))).thenReturn(mockParam);

        // set expected response
        DataResult<Long> expectedResponse = new DataResult<>();
        expectedResponse.setData(123L);
        when(operationService.createWithPermission(any(OperationSavedParam.class))).thenReturn(expectedResponse);

        // get actual response
        DataResult<Long> actualResponse = operationSavedController.create(request);

        // Assert
        assertNotNull(actualResponse);
        assertEquals(expectedResponse.getData(), actualResponse.getData());

        // Verify interactions
        verify(operationWebConverter).req2param(any(OperationCreateRequest.class));
        verify(operationService).createWithPermission(any(OperationSavedParam.class));
    }


}
