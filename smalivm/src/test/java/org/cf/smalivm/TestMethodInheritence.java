package org.cf.smalivm;

import static org.junit.Assert.assertEquals;

import org.cf.smalivm.context.ExecutionGraph;
import org.cf.smalivm.context.HeapItem;
import org.cf.smalivm.context.MethodState;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

@RunWith(Enclosed.class)
public class TestMethodInheritence {

    public static class ChildClass {
        private static final String CLASS_NAME = "Lchild_class;";
        private static final String EXPECTED_VALUE = "abstract?";
        private VirtualMachine vm;

        @Before
        public void setupVM() {
            vm = VMTester.getTestVM();
        }

        @Test
        public void testInvokingAbstractMethodThroughParentReferenceWorks() {
            String methodName = "callsAbstractMethod()Ljava/lang/String;";
            ExecutionGraph graph = vm.execute(CLASS_NAME + "->" + methodName);
            HeapItem item = graph.getTerminatingRegisterConsensus(MethodState.ReturnRegister);
            String value = (String) item.getValue();

            assertEquals(EXPECTED_VALUE, value);
        }

        @Test
        public void testInvokingImplemenetedAbstractMethodWorks() {
            String methodName = "abstractMethod()Ljava/lang/String;";
            ExecutionGraph graph = vm.execute(CLASS_NAME + "->" + methodName);
            HeapItem item = graph.getTerminatingRegisterConsensus(MethodState.ReturnRegister);
            String value = (String) item.getValue();

            assertEquals(EXPECTED_VALUE, value);
        }

        @Test
        public void testInvokingParentMethodWorks() {
            String methodName = "callsParentMethod()Ljava/lang/String;";
            ExecutionGraph graph = vm.execute(CLASS_NAME + "->" + methodName);
            HeapItem item = graph.getTerminatingRegisterConsensus(MethodState.ReturnRegister);
            String value = (String) item.getValue();

            assertEquals("parentMethod", value);
        }
    }

    public static class ParentClass {
        private static final String CLASS_NAME = "Lparent_class;";

        private VirtualMachine vm;

        @Before
        public void setupVM() {
            vm = VMTester.getTestVM();
        }

        @Test
        public void testExecutingAbstractMethodReturnsNull() {
            String methodName = "abstractMethod()Ljava/lang/String;";
            ExecutionGraph graph = vm.execute(CLASS_NAME + "->" + methodName);

            assertEquals(null, graph);
        }
    }

}
