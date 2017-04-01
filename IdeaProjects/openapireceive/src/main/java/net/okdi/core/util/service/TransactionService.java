package net.okdi.core.util.service;


import javax.annotation.Resource;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.data.mongodb.core.MongoDbUtils;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.CannotCreateTransactionException;
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.TransactionSystemException;

import com.mongodb.BasicDBObject;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.CommandResult;
import com.mongodb.DB;
import com.mongodb.DBObject;

@Component("transactionService")
@Aspect
public class TransactionService  {

	
	@Resource
	protected MongoTemplate mongoTemplate;

	@Pointcut(value = "execution(* net.okdi.*.service.*.*(..))")
	public void pointcutV2() {
	}
//	@Pointcut(value = "execution(* net.okdi.o2o.apiV3.service.*.*(..))")
//	public void pointcutV3() {
//	}
//	@Pointcut(value = "execution(* net.okdi.o2o.igomall.service.*.*(..))")
//	public void igomall() {
//	}
	private static ThreadLocal<Boolean> connThreadLocal = new ThreadLocal<Boolean>()
	{
        @Override
        protected Boolean initialValue() {
            return false;
        }
    };


	@Around(value = "pointcutV2()")
	public Object hanldeMongoTransaction(ProceedingJoinPoint joinPoint) throws Throwable {
		Object proceed = null;
		// MethodSignature ms = (MethodSignature) joinPoint.getSignature();
		// Method m = ms.getMethod();
		try {
			if (!connThreadLocal.get()) {
				beginMongoTransaction();
				connThreadLocal.set(true);
				proceed = joinPoint.proceed();
				commitMongoTransaction();
				connThreadLocal.remove();
			} else {
				proceed = joinPoint.proceed();
			}
			return proceed;
		} catch (Exception e) {
			rollbackMongoTransaction();
			connThreadLocal.remove();
			throw e;
		}
	}
	
	// Serializable
	// 这是最高的隔离级别，它通过强制事务排序，使之不可能相互冲突，从而解决幻读问题。简言之，它是在每个读的数据行上加上共享锁。
	// 在这个级别，可能导致大量的超时现象和锁竞争。
	// mvcc
	// mysql 用的事务管理行级锁,当一个事务对a更改成功对b更改失败,这样他要回滚b的操作,但是另一个事务也要对b操作
	// 这样第一个事务将不对a进行回滚,b得到没有回滚之前的数据
	private void beginMongoTransaction() throws TransactionException {
		BasicDBObjectBuilder builder = new BasicDBObjectBuilder().add(
				"beginTransaction", Boolean.TRUE);
		// builder.append("isolation", "readUncommitted");
		// builder.append("isolation", "serializable");
		builder.append("isolation", "mvcc");
		DBObject command = builder.get();
		CommandResult result = null;
		DB mongoDB = null;
		try {
			mongoDB = mongoTemplate.getDb();
			result = mongoDB.command(command);
			
		} catch (Exception ex) {
			MongoDbUtils.closeDB(mongoDB);
			ex.printStackTrace();
		}
		String error = result.getErrorMessage();
		if (error != null) {
			MongoDbUtils.closeDB(mongoDB);
			throw new CannotCreateTransactionException("execution of "
					+ command.toString() + " failed: " + error);
		}
	}

	private void commitMongoTransaction() throws TransactionException {
		DBObject command = new BasicDBObject("commitTransaction", Boolean.TRUE);
		CommandResult result = null;
		DB mongoDB = null;
		try {
			mongoDB = mongoTemplate.getDb();
			result = mongoDB.command(command);
		} catch (RuntimeException ex) {
			ex.printStackTrace();
			throw new TransactionSystemException(
					"tokuMx.doCommit: unexpected system exception: "
							+ ex.getMessage(), ex);
		} finally {
			MongoDbUtils.closeDB(mongoDB);
		}
		String error = result.getErrorMessage();
		if (error != null) {
//			throw new TransactionSystemException(
//					"tokuMx.doCommit: execution of " + command.toString()
//							+ " failed: " + error);
		}
	}

	private void rollbackMongoTransaction() throws TransactionException {
		DBObject command = new BasicDBObject("rollbackTransaction",
				Boolean.TRUE);
		CommandResult result = null;
		DB mongoDB = null;
		boolean flag = connThreadLocal.get() == null ? false : connThreadLocal.get();
		if (!flag) {
			return;
		}
		try {
			mongoDB = mongoTemplate.getDb();
			result = mongoDB.command(command);
		} catch (RuntimeException ex) {
			ex.printStackTrace();
			throw new TransactionSystemException(
					"tokuMx.doRollback: unexpected system exception: "
							+ ex.getMessage(), ex);
		} finally {
			MongoDbUtils.closeDB(mongoDB);
		}
		String error = result.getErrorMessage();
		if (error != null) {
			throw new TransactionSystemException(
					"tokuMx.doRollback: execution of " + command.toString()
							+ " failed: " + error);
		}
	}

}