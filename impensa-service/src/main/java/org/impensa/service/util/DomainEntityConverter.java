/*
 * This file is part of impensa.
 * CopyLeft (C) BigBang<->BigCrunch.All Rights are left.
 *
 * 1) Modify it if you can understand.
 * 2) If you distribute a modified version, you must do it at your own risk.
 *
 */
package org.impensa.service.util;

import java.lang.reflect.Field;
import org.commons.reflection.ClazzUtil;
import org.impensa.service.db.entity.mapping.MappingEntity;
import org.impensa.service.db.entity.mapping.Property;

/**
 * This works this way.
 * Assuming fields of  entity and domain object constitute two sets X and Y  respectively.
 * This utility function makes an assumption that function from X->Y is an onto  function.
 * @author manosahu
 */
public class DomainEntityConverter {

    /**
     * This converts domainObject to entityobject as indicated in MappingEntity annotation
     * of the domainObject. 
     * This returns null if MappingEntity annotation is not present in the domain object's class.
     * Domain object controls the way data will be moved across.
     * The philosophy is that we can have many domain objects for the same entity.
     * Let the entity be free from all those crap.
     * @param <T>
     * @param domainObject
     * @param t
     * @return
     * @throws Exception 
     */
     public static <T> T toEntity(Object domainObject, Class<T> t) throws Exception {
        
         Class domainClazz = domainObject.getClass();
         
         MappingEntity mappingEntity = (MappingEntity)domainClazz.getAnnotation(MappingEntity.class);
         
         if(mappingEntity == null){
             return null;
         }
         if(!mappingEntity.name().equals(t)){
             return null;
         }
             
         
         Class entityClazz = mappingEntity.name();
         
         Field [] fields = domainClazz.getDeclaredFields();
         
         Object entityObject = ClazzUtil.instantiate(entityClazz);
         
         for(Field field : fields){
             
             String getterMethodName = ClazzUtil.getGetterMethod(field.getName());
         
             String setterMethodName;
             
             if(field.isAnnotationPresent(Property.class)){
             
                 Property property =(Property)field.getAnnotation(Property.class);
                 
                 setterMethodName = ClazzUtil.getSetterMethod(property.name());
             }else{
                 setterMethodName = ClazzUtil.getSetterMethod(field.getName());
             }
             
             Object setterData = ClazzUtil.invoke(domainObject, getterMethodName,new Class[]{},new Object[]{} );
             
             
             
             ClazzUtil.invoke(entityObject,setterMethodName, new Class[]{field.getType()}, new Object[]{setterData});
         }
         
         T result = (T)entityObject;
         
         return result;
     }
     
     /**
      * This is same as toEntity with the roles reversed.
      * However we do not want to clutter the entity object with more annotation.
      * Hence domain object will bear those annotations.
      * @param <T>
      * @param entityObject
      * @param t
      * @return
      * @throws Exception 
      */
     public static <T> T toDomain(Object entityObject ,Class<T> t) throws Exception {
        
        
         MappingEntity mappingEntity = (MappingEntity)t.getAnnotation(MappingEntity.class);
         
         if(mappingEntity == null){
             return null;
         }
         Class entityClazz = mappingEntity.name();
         
         if(!entityClazz.equals(entityObject.getClass())){
         
             return null;
         }
         Field [] fields = t.getDeclaredFields();
         
         Object domainObject = ClazzUtil.instantiate(t);
         
         for(Field field : fields){
             
             String getterMethodName = ClazzUtil.getGetterMethod(field.getName());
         
             String setterMethodName;
             
             if(field.isAnnotationPresent(Property.class)){
             
                 Property property =(Property)field.getAnnotation(Property.class);
                 
                 setterMethodName = ClazzUtil.getSetterMethod(property.name());
             }else{
                 
                 setterMethodName = ClazzUtil.getSetterMethod(field.getName());
             }
             
             Object setterData = ClazzUtil.invoke(entityObject, getterMethodName,new Class[]{},new Object[]{} );
             
             
             
             ClazzUtil.invoke(domainObject,setterMethodName, new Class[]{field.getType()}, new Object[]{setterData});
         }
         
         T result = (T)domainObject;
         
         return result;
     }
     
}
