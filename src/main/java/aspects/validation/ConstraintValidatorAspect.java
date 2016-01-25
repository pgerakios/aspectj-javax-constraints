package aspects.validation;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import javax.validation.constraints.NotNull;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

@Aspect
public class ConstraintValidatorAspect {

     //@Around("(execution(public * * (.., @javax.validation.constraints..* (*), ..)) || execution(@javax.validation.constraints.* public * * (..)))&& within(test.company.com..*)")
    // Apply this aspect to all classes using the notnull annotation
    @Around("(execution(public * * (.., @javax.validation.constraints..* (*), ..)) || execution(@javax.validation.constraints.* public * * (..)))")
    public Object pointcutMethodArgument(ProceedingJoinPoint joinPoint) throws Throwable {
        return validateInvocation(joinPoint);
    }

    private Object validateInvocation(ProceedingJoinPoint aJoinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) aJoinPoint.getSignature();
        String methodName = signature.getMethod().getName();
        Class<?>[] parameterTypes = signature.getMethod().getParameterTypes();
        Method method = aJoinPoint.getTarget().getClass().getMethod(methodName,parameterTypes);
        Annotation[][] annotations =  method.getParameterAnnotations();
        Object[] args = aJoinPoint.getArgs();

        for(int i = 0 ; i < annotations.length ; i++) {
            if(args[i] != null) continue; // fast path
            if(annotations[i] != null) {
                for(Annotation annotation : annotations[i]) {
                    if(annotation == null) continue;
                    if(annotation.annotationType().equals(NotNull.class)) {
                        throw new NullPointerException("@NotNull constraint violation of actual parameter #" + (i+1) + ". Method: "+ aJoinPoint.getThis().getClass().getName() + "." + methodName + " Formal parameter type:" + parameterTypes[i].getName());
                    }
                }
            }
        }
        Object retVal = aJoinPoint.proceed();
        if(retVal == null && (!method.getReturnType().equals(void.class) && !method.getReturnType().equals(Void.class))) {
            Annotation[] methodAnnotations = method.getAnnotations();
            for (Annotation annotation : methodAnnotations) {
                if (annotation != null && annotation.annotationType().equals(NotNull.class)) {
                    throw new NullPointerException("@NotNull constraint violation of return value. Method: " + aJoinPoint.getThis().getClass().getName() + "." + methodName);
                }
            }
        }
        return retVal;
    }
}