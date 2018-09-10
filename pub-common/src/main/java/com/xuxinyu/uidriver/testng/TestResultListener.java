package com.xuxinyu.uidriver.testng;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

/**
 * 实现对testng测试结果的监听器
 * */
public class TestResultListener extends TestListenerAdapter {
	
	@Override
	/*onFinish方法在所有的测试方法都运行结束，在执行的方法*/
	public void onFinish(ITestContext testContext){
		super.onFinish(testContext);
		
		//测试结果的List，用于去重处理
		List<ITestResult> testsToBeRemoved = new ArrayList<>();
		
		// collect all id's from passed test对所有通过的用例进行id收集
		Set<Integer> passTestIds = new HashSet<>();
		for(ITestResult passtest:testContext.getPassedTests().getAllResults()){
			System.out.println("PassedTests = " + passtest.getName());
			
			int passTestId = getId(passtest);
			//因为在用于dataprovider时候，pass的结果也存在多次重复，所以这里也需要进行去重
			if(passTestIds.contains(passTestId)){
				testsToBeRemoved.add(passtest);
			}else{
			passTestIds.add(getId(passtest));
			}
		}
		
		
		Set<Integer> failTestIds = new HashSet<>();
		for(ITestResult failtest:testContext.getFailedTests().getAllResults()){
			System.out.println("PassedTests = " + failtest.getName());
			
			int failTestId = getId(failtest);
			
			//如果失败的结果set中包含这个failTestId，或者通过的结果set中包含这个failTestId
			//那么，将这个id对于的result加入将要删除的list中
			//否则，都不包含，则加入失败set中
			if (failTestIds.contains(failTestId) || passTestIds.contains(failTestId)) {
				testsToBeRemoved.add(failtest);
				System.out.println("Remove repeat Fail Test: " + failtest.getName());
			} else {	
				failTestIds.add(failTestId);
			}
		}
		
		//和failresult一个处理逻辑
		Set<Integer> skipTestIds = new HashSet<Integer>();
		for (ITestResult skipTest : testContext.getSkippedTests().getAllResults()) {
			System.out.println("skipTest = " + skipTest.getName());
			// id = class + method + dataprovider
			int skipTestId = getId(skipTest);

			if (skipTestIds.contains(skipTestId) || passTestIds.contains(skipTestId)
					|| failTestIds.contains(skipTestId)) {
				testsToBeRemoved.add(skipTest);
			} else {
				skipTestIds.add(skipTestId);
			}
		}
		
		// finally delete all tests that are marked
		//对最终的标识结果进行删除
		for (Iterator<ITestResult> iterator = testContext.getPassedTests().getAllResults().iterator(); iterator
				.hasNext();) {
			ITestResult testResult = iterator.next();
			if (testsToBeRemoved.contains(testResult)) {
				System.out.println("Remove repeat Sucess Test: " + testResult.getName());
				iterator.remove();
			}
		}
				
				for (Iterator<ITestResult> iterator = testContext.getFailedTests().getAllResults().iterator(); iterator
						.hasNext();) {
					ITestResult testResult = iterator.next();
					if (testsToBeRemoved.contains(testResult)) {
						System.out.println("Remove repeat Fail Test: " + testResult.getName());
						iterator.remove();
					}
				}
				for (Iterator<ITestResult> iterator = testContext.getSkippedTests().getAllResults().iterator(); iterator
						.hasNext();) {
					ITestResult testResult = iterator.next();
					if (testsToBeRemoved.contains(testResult)) {
						System.out.println("Remove repeat Skip Test: " + testResult.getName());
						iterator.remove();
					}
				}
		
	}
	
	/*获取结果集中的class、method等的hashcode*/
	private int getId(ITestResult result){
		int id = result.getTestClass().getName().hashCode();
		id = id + result.getMethod().getMethodName().hashCode();
		id = id + (result.getParameters() != null ? Arrays.hashCode(result.getParameters()) : 0);
		return id;
	}

}
