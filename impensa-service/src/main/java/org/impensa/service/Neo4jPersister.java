package org.impensa.service;

import org.impensa.service.db.entity.Org;
import org.impensa.service.db.entity.Role;
import org.impensa.service.db.entity.User;
import org.impensa.service.db.entity.UserAssignedOrgRelationship;
import org.impensa.service.db.repository.OrgRepository;
import org.impensa.service.db.repository.RoleRepository;
import org.impensa.service.db.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author: rkowalewski
 */
@Component
@Transactional
public class Neo4jPersister {

    @Autowired
    public UserRepository userRepository;

    @Autowired
    public OrgRepository orgRepository;

    @Autowired
    public RoleRepository roleRepository;

    private User createUser(String id, String name) {
       return null;// return userRepository.save(new User(id, name));
    }

    private Org createOrg(String id, String name , String desc , Org parentOrg){
        Org org = new Org();
        org.setOrgId(id);
        org.setOrgName(name);
        org.setOrgDescription(desc);
        org.setParentOrg(parentOrg);
        return orgRepository.save(org);
    }

    private Role createRole(String id, String name) {
        return roleRepository.save(new Role(id, name));
    }

    public void createTestData() {
        /*
         // System.out.println(userRepository.findByUserId("SP1").getUserName());
         UserOld jordan = createUser("MJ", "Monika Jordan");
         UserOld pippen = createUser("SP", "Sandra Pippen");
         UserOld miller = createUser("JM", "John Miller");

         Product pizzaMargarita = createProduct("Pizza_1", "Pizza Margarita");
         Product pizzaFungi = createProduct("Pizza_2", "Pizza Fungi");
         Product pizzaSalami = createProduct("Pizza_3", "Pizza Salami");
         Product pizzaVegitarian = createProduct("Pizza_4", "Pizza Vegitarian");
         Product pizzaRustica = createProduct("Pizza_5", "Pizza Rustica");

         userClickedProduct(jordan, pizzaMargarita);
         userClickedProduct(jordan, pizzaFungi);
         userClickedProduct(jordan, pizzaSalami);

         userClickedProduct(pippen, pizzaMargarita);
         userClickedProduct(pippen, pizzaVegitarian);
         userClickedProduct(pippen, pizzaRustica);
         userClickedProduct(pippen, pizzaMargarita);
         userClickedProduct(pippen, pizzaVegitarian);

         userClickedProduct(miller, pizzaFungi);
        
         */
        
        User ms = createUser("MS", "Meehika Sahoo");
        createUser("MS", "Meehika Sahoo");
        Org og1 = createOrg("aa", "kids" ,"aaadfd",null);
        Org og2 = createOrg("aaa", "kidsa" ,"aaadfd",og1);

       // System.out.println(ms.getUserName());
        Role rol1 = createRole("r1", "role1");

        Role rol2 = createRole("r2", "role1");

        if (!ms.isOrgAssigned(og1)) {
            ms.assignOrg(og1);
            userRepository.save(ms);

            orgRepository.save(og1);
        }

        og1.assignRole(rol2);
      //  og1.assignRole(rol2);

        orgRepository.save(og1);
        roleRepository.save(rol2);
        
        ms.assignRole(rol1);
        userRepository.save(ms);
        roleRepository.save(rol1);
        
        User ms1 = userRepository.findByUserId("MS");

        for (UserAssignedOrgRelationship uar : ms1.getAssignedOrgs()) {
            System.out.println(" org is " + uar.getOrg().getOrgDescription());
            System.out.println(" org is " + uar.getOrg().getAssignedRoles().size());
        }
        
        System.out.println(ms1.findAssignedRoles());
        System.out.println(ms1.getAssignedOrgs().size());

    }

}
