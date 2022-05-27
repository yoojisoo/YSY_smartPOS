package com.ysy.jwt.auth.dto;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

/**
 * 
 * @author clubbboy@naver.com
 *
 * @param <T>
 */
public class EntityToDto<T> {
	private T t;
	
	public T get() {
		return this.t;
	}
//	private T myClass;
//	public EntityToDto(T t ){
//		this.myClass = t;
//	}
//	public T getT() {
//		return this.myClass;
//				
//	}
	
	
	public EntityToDto(T t , Object[] objArr){
		this.t =t;
		try 
		{
			String className = t.getClass().getName();
			Class targetClass = null;
			
		        // class가 존재하지 않을 경우 ClassNotFoundException 발생
	        targetClass = Class.forName(className);
//			T newInstance = (T) targetClass.newInstance();
			Method [] methods = targetClass.getDeclaredMethods();
			Field[]    fields = targetClass.getDeclaredFields();
			
				
			for(int j = 0 ; j < objArr.length ; j++) {
				Field field = fields[j];
				String fildName = field.getName();
				for (int i = 0; i < methods.length; i++) 
				{
					Method method = methods[i];
			        String methodName = method.getName();
			        String reMethodName = methodName.replace("set", ""); 
			        if(methodName.indexOf("set") == 0 && 
			           fildName.toUpperCase().equals(reMethodName.toUpperCase())) 
			        {
			        	System.out.println("methodName = "+methodName);
			        	System.out.println("class = " + method.getDeclaringClass());
			        	Class paramTypes[] = method.getParameterTypes();
			            for(Class p : paramTypes) {
			               System.out.println("param type : " + p);
			            }
			            System.out.println("return type : "+method.getReturnType());
			        	method.invoke(targetClass  
										, (	objArr[j] instanceof String ?(String)objArr[j] : 
											objArr[j] instanceof Integer?(Integer)objArr[j] :
											objArr[j] instanceof Double ?(Double)objArr[j] :
											objArr[j] instanceof Long   ?(Long)objArr[j] :objArr[j]
										  )
									 );
						break;
			        }
				}
			}
				
			
		} 
		catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		catch(ClassNotFoundException e) {
			e.printStackTrace();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		
		
		
		
	}
}
