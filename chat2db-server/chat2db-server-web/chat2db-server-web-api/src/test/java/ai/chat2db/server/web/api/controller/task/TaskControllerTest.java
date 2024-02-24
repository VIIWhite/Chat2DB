package ai.chat2db.server.web.api.controller.task;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

import ai.chat2db.server.domain.api.model.Task;
import ai.chat2db.server.domain.api.param.TaskPageParam;
import ai.chat2db.server.domain.api.service.TaskService;
import ai.chat2db.server.tools.base.wrapper.result.PageResult;
import ai.chat2db.server.tools.base.wrapper.result.web.WebPageResult;
import ai.chat2db.server.tools.common.util.ContextUtils;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.springframework.boot.test.context.SpringBootTest;
import org.mockito.MockitoAnnotations;
import org.junit.After;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
public class TaskControllerTest {

    @InjectMocks
    private TaskController taskController;

    @Mock
    private TaskService taskService;

    private MockedStatic<ContextUtils> mockedContextUtils;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        // Mock ContextUtils.getUserId() to return a specific user ID
        mockedContextUtils = mockStatic(ContextUtils.class);
        mockedContextUtils.when(ContextUtils::getUserId).thenReturn(1L);
    }

    @After
    public void tearDown() {
        mockedContextUtils.close();  // Clean static mock
    }

    @Test
    public void testList() {
        // Prepare mock TaskPageParam and mock PageResult
        TaskPageParam mockTaskPageParam = new TaskPageParam();
        mockTaskPageParam.setPageNo(1);
        mockTaskPageParam.setPageSize(10);
        mockTaskPageParam.setUserId(1L); // Assuming ContextUtils.getUserId() returns 1L

        PageResult<Task> mockPageResult = new PageResult<>();

        // When taskService.page is called with any TaskPageParam, return mockPageResult
        when(taskService.page(any(TaskPageParam.class))).thenReturn(mockPageResult);

        // Call the method
        WebPageResult<Task> result = taskController.list();

        // Verify the interactions and the result
        verify(taskService).page(any(TaskPageParam.class));
        assertNotNull(result); // Verify the result is not null
    }

    @Test
    public void testListWithEmptyResult() {
        TaskPageParam mockTaskPageParam = new TaskPageParam();
        mockTaskPageParam.setPageNo(1);
        mockTaskPageParam.setPageSize(10);
        mockTaskPageParam.setUserId(1L);

        PageResult<Task> mockPageResult = new PageResult<>();
        mockPageResult.setData(Collections.emptyList()); // Return an empty list

        when(taskService.page(any(TaskPageParam.class))).thenReturn(mockPageResult);

        WebPageResult<Task> result = taskController.list();

        assertNotNull(result);
        assertTrue(result.getData().getData().isEmpty()); // Verify the data list is empty
        assertEquals(Long.valueOf(100), result.getData().getTotal()); // Assuming the total is still 100 for the test
    }

    @Test(expected = RuntimeException.class)
    public void testListWhenServiceThrowsException() {
        when(taskService.page(any(TaskPageParam.class))).thenThrow(new RuntimeException("Service exception"));

        taskController.list(); // This call should throw the RuntimeException
    }
}
