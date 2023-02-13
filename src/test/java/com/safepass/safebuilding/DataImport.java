package com.safepass.safebuilding;

import com.github.javafaker.Faker;
import com.safepass.safebuilding.common.meta.*;
import com.safepass.safebuilding.flat_type.entity.FlatType;
import org.junit.jupiter.api.Test;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public class DataImport {

    private List<UUID> flatTypeIds;
    private List<UUID> facilityIds;
    private List<UUID> flatIds;
    private List<UUID> serviceIds;
    private List<UUID> customerIds;

    private List<UUID> buildingIds;
    private List<UUID> walletIds;
    private List<UUID> rentContractIds;
    private List<UUID> billIds;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    private Faker faker = new Faker();
    @Test
    public String createAdmin() {

        List<String> sqls = new ArrayList<>();

        for (int i = 0; i < 20; i++) {
            StringBuilder sql = new StringBuilder("INSERT INTO admin (id, email, fullname, password, status, phone) VALUES (");
            sql.append("\"").append(UUID.randomUUID()).append("\", \"") //id
                    .append(faker.name().lastName()).append("@gmail.com\", \"") //email
                    .append(faker.name().fullName()).append("\", \"") //fullname
                    .append(faker.number().randomNumber()).append("\", \"") //password
                    .append(AdminStatus.ACTIVE).append("\", \"")
                    .append(faker.phoneNumber().cellPhone()).append("\");");
            sqls.add(sql.toString());
        }
        String output = "";
        for (String e: sqls) {
//            System.out.print(e);
            output = output + e;
        }
        return output;
    }

    //@Test
    public String createFlatType() {
        List<String> sqls = new ArrayList<>();
        flatTypeIds = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            flatTypeIds.add(UUID.randomUUID());
        }
        for (int i = 0; i < 50; i++) {
            StringBuilder sql = new StringBuilder("INSERT INTO flat_type (id, description, name, status) VALUES (\"");
            sql.append(flatTypeIds.get(i)).append("\", \"") //id
                    .append(faker.commerce().department()).append("\", \"") //description
                    .append(faker.name().name()).append("\", \"") //name
                    .append(FlatTypeStatus.ACTIVE).append("\");");
            sqls.add(sql.toString());
        }
        String output = "";
        for (String e: sqls) {
//            System.out.print(e);
            output = output + e;
        }
        return output;
    }

//    @Test
    public String createService() {
        List<String> sqls = new ArrayList<>();
        serviceIds = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            serviceIds.add(UUID.randomUUID());
        }
        for (int i = 0; i < 10; i++) {
            StringBuilder sql = new StringBuilder("INSERT INTO service (id, name, description, price, status) VALUES (\"");
            sql.append(serviceIds.get(i)).append("\", \"") //id
                    .append(faker.name().name()).append("\", \"")
                    .append(faker.job().keySkills()).append("\", ")
                    .append(faker.commerce().price()).append(", \"")
                    .append(ServiceStatus.ACTIVE)
                    .append("\");");

            sqls.add(sql.toString());
        }
        String output = "";
        for (String e: sqls) {
//            System.out.print(e);
            output = output + e;
        }
        return output;
    }

//    @Test
    public String createCustomer() {
        List<String> sqls = new ArrayList<>();

        customerIds = new ArrayList<>();
        for (int i = 0; i < 60; i++) {
            customerIds.add(UUID.randomUUID());
        }
        for (int i = 0; i < 15; i++) {
            StringBuilder sql = new StringBuilder("INSERT INTO customer (id, password, fullname, date_of_birth, gender, email, phone, address, citizen_id, date_join, status) VALUES (\"");
            sql.append(customerIds.get(i)).append("\", ") //id
                    .append(faker.number().numberBetween(10000000, 99999999)).append(", \"")
                    .append(faker.name().fullName()).append("\", \"")
                    .append(sdf.format(faker.date().birthday())).append("\", \"")
                    .append(Gender.FEMALE).append("\", \"")
                    .append(faker.name().username()).append("@gmail.com\", \"")
                    .append(faker.phoneNumber().cellPhone()).append("\", \"")
                    .append(faker.address().fullAddress()).append("\", \"")
                    .append(faker.business().creditCardNumber()).append("\", \"")
                    .append(sdf.format(faker.date().between(Date.valueOf("2020-12-01"), Date.valueOf("2022-12-29")))).append("\", \"")
                    .append(CustomerStatus.ACTIVE)
                    .append("\");");

            sqls.add(sql.toString());
        }

        for (int i = 15; i < 30; i++) {
            StringBuilder sql = new StringBuilder("INSERT INTO customer (id, password, fullname, date_of_birth, gender, email, phone, address, citizen_id, date_join, status) VALUES (\"");
            sql.append(customerIds.get(i)).append("\", ") //id
                    .append(faker.number().numberBetween(10000000, 99999999)).append(", \"")
                    .append(faker.name().fullName()).append("\", \"")
                    .append(sdf.format(faker.date().birthday())).append("\", \"")
                    .append(Gender.MALE).append("\", \"")
                    .append(faker.name().username()).append("@gmail.com\", \"")
                    .append(faker.phoneNumber().cellPhone()).append("\", \"")
                    .append(faker.address().fullAddress()).append("\", \"")
                    .append(faker.business().creditCardNumber()).append("\", \"")
                    .append(sdf.format(faker.date().between(Date.valueOf("2020-12-01"), Date.valueOf("2022-12-29")))).append("\", \"")
                    .append(CustomerStatus.ACTIVE)
                    .append("\");");

            sqls.add(sql.toString());
        }


        for (int i = 30; i < 45; i++) {
            StringBuilder sql = new StringBuilder("INSERT INTO customer (id, password, fullname, date_of_birth, gender, email, phone, address, citizen_id, date_join, status) VALUES (\"");
            sql.append(customerIds.get(i)).append("\", ") //id
                    .append(faker.number().numberBetween(10000000, 99999999)).append(", \"")
                    .append(faker.name().fullName()).append("\", \"")
                    .append(sdf.format(faker.date().birthday())).append("\", \"")
                    .append(Gender.FEMALE).append("\", \"")
                    .append(faker.name().username()).append("@gmail.com\", \"")
                    .append(faker.phoneNumber().cellPhone()).append("\", \"")
                    .append(faker.address().fullAddress()).append("\", \"")
                    .append(faker.business().creditCardNumber()).append("\", \"")
                    .append(sdf.format(faker.date().between(Date.valueOf("2020-12-01"), Date.valueOf("2022-12-29")))).append("\", \"")
                    .append(CustomerStatus.INACTIVE)
                    .append("\");");

            sqls.add(sql.toString());
        }

        for (int i = 45; i < 60; i++) {
            StringBuilder sql = new StringBuilder("INSERT INTO customer (id, password, fullname, date_of_birth, gender, email, phone, address, citizen_id, date_join, status) VALUES (\"");
            sql.append(customerIds.get(i)).append("\", ") //id
                    .append(faker.number().numberBetween(10000000, 99999999)).append(", \"")
                    .append(faker.name().fullName()).append("\", \"")
                    .append(sdf.format(faker.date().birthday())).append("\", \"")
                    .append(Gender.MALE).append("\", \"")
                    .append(faker.name().username()).append("@gmail.com\", \"")
                    .append(faker.phoneNumber().cellPhone()).append("\", \"")
                    .append(faker.address().fullAddress()).append("\", \"")
                    .append(faker.business().creditCardNumber()).append("\", \"")
                    .append(sdf.format(faker.date().between(Date.valueOf("2020-12-01"), Date.valueOf("2022-12-29")))).append("\", \"")
                    .append(CustomerStatus.INACTIVE)
                    .append("\");");

            sqls.add(sql.toString());
        }
        String output = "";
        for (String e: sqls) {
//            System.out.print(e);
            output = output + e;
        }
        return output;
    }

//    @Test
    String createBuilding() {
        List<String> sqls = new ArrayList<>();
        buildingIds = new ArrayList<>();

        for (int i = 0; i < 4; i++) {
            buildingIds.add(UUID.randomUUID());
        }
        for (int i = 0; i < 4; i++) {
            StringBuilder sql = new StringBuilder("INSERT INTO building (id, name, status) VALUES (\"");
            sql.append(buildingIds.get(i)).append("\", \"") //id
                    .append(faker.name().name()).append("\", \"")
                    .append(BuildingStatus.AVAILABLE)
                    .append("\");");

            sqls.add(sql.toString());
        }
        String output = "";
        for (String e: sqls) {
//            System.out.print(e);
            output = output + e;
        }
        return output;
    }

//    @Test
    public String createFlat() {
//        createBuilding();
//        createFlatType();

        List<String> sqls = new ArrayList<>();
        flatIds = new ArrayList<>();
        for (int i = 0; i < 200; i++) {
            flatIds.add(UUID.randomUUID());
        }
        for (int i = 1; i <= 10; i++) {
            for (int j = 1; j <= 20; j++) {
                StringBuilder sql = new StringBuilder("INSERT INTO flat (id, detail, price, status, flat_type_id, building_id, room_number) VALUES (\"");
                sql.append(flatIds.get((j-1)*10+i-1)).append("\", \"") //id
                        .append(faker.commerce().department()).append("\", ")
                        .append(faker.number().numberBetween(3,30)).append(", \"")
                        .append(FlatStatus.AVAILABLE).append("\", \"")
                        .append(flatTypeIds.get(random(0, flatTypeIds.size() - 1))).append("\", \"")
                        .append(buildingIds.get(random(0, buildingIds.size() - 1))).append("\", ")
                        .append((j-1)*10+i-1+1)
                        .append(");");

                sqls.add(sql.toString());
            }

        }
        String output = "";
        for (String e: sqls) {
//            System.out.print(e);
            output = output + e;
        }
        return output;

    }

    public String createFacility() {
        List<String> sqls = new ArrayList<>();
        facilityIds = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            facilityIds.add(UUID.randomUUID());
        }
        for (int i = 0; i < 6; i++) {
            StringBuilder sql = new StringBuilder("INSERT INTO facility (id, item, quantity, status) VALUES (\"");
            sql.append(facilityIds.get(i)).append("\", \"") //id
                    .append(faker.name().name()).append("\", ") //item
                    .append(random(1,5)).append(", \"") //quantity
                    .append(FacilityStatus.AVAILABLE) //status
                    .append("\");");

            sqls.add(sql.toString());
        }
        String output = "";
        for (String e: sqls) {
//            System.out.print(e);
            output = output + e;
        }
        return output;
    }

//    @Test
    public String createFlatFacility() {
//        createBuilding();
//        createFlatType();
//        createFlat();
//        createFacility();

        List<String> sqls = new ArrayList<>();

        for (UUID facilityId : facilityIds) {
            for (UUID flatId : flatIds) {
                if (random(0, 1) > 0) {
                    StringBuilder sql = new StringBuilder("INSERT INTO flat_facility (flat_id, facility_id) VALUES (\"");
                    sql.append(flatId).append("\", \"") //id
                            .append(facilityId)
                            .append("\");");
                    sqls.add(sql.toString());
                }
            }
        }
        String output = "";
        for (String e: sqls) {
//            System.out.print(e);
            output = output + e;
        }
        return output;
    }

//    @Test
    public String   createWallet() {

//        createCustomer();
        List<String> sqls = new ArrayList<>();
        walletIds = new ArrayList<>();
        for (UUID customerId : customerIds) {
            UUID walletId = UUID.randomUUID();
            walletIds.add(walletId);
            StringBuilder sql = new StringBuilder("INSERT INTO wallet (id, amount, status, customer_id) VALUES (\"");
            sql.append(walletId).append("\", ")
                    .append(random(50000, 10000000)).append(", \"")
                    .append(WalletStatus.ACTIVE).append("\", \"")
                    .append(customerId)
                    .append("\");");
            sqls.add(sql.toString());
        }
        String output = "";
        for (String e: sqls) {
//            System.out.print(e);
            output = output + e;
        }
        return output;
    }

//    @Test
    String createRentContract() {
//        createCustomer();
//        createBuilding();
//        createFlatType();
//        createFlat();

        List<String> sqls = new ArrayList<>();
        rentContractIds = new ArrayList<>();
        for (UUID flatId : flatIds) {
            int rand;
            int count = 0;
            if ((rand = random(0,2)) > 0) {
                count += rand;
                UUID rentContractId = UUID.randomUUID();
                rentContractIds.add(rentContractId);
                StringBuilder sql = new StringBuilder("INSERT INTO rent_contract (id, contract, expiry_date, status, value, customer_id, flat_id) VALUES (\"");
                sql.append(rentContractId).append("\", \"")
                        .append(faker.business().creditCardNumber()).append("\", \"")
                        .append(faker.business().creditCardExpiry()).append("\", \"")
                        .append(RentContractStatus.VALID).append("\", ")
                        .append(random(5000000, 30000000)).append(", \"")
                        .append(customerIds.get(count)).append("\", \"")
                        .append(flatId)
                        .append("\");");
                sqls.add(sql.toString());
            }


        }
        String output = "";
        for (String e: sqls) {
//            System.out.print(e);
            output = output + e;
        }
        return output;
    }

//    @Test
    String createMoneyTransfer() {
//        createWallet();
        List<String> sqls = new ArrayList<>();
        for (UUID walletId : walletIds) {
            int rand = random(3, 10);
            for (int j = 0; j < rand; j++) {
                UUID moneyTransferId = UUID.randomUUID();
                StringBuilder sql = new StringBuilder("INSERT INTO money_transfer (id, amount, date, status, type, wallet_id) VALUES (\"");
                sql.append(moneyTransferId).append("\", ")
                        .append(random(500, 15000) * 1000).append(", \"")
                        .append(sdf.format(faker.date().between(Date.valueOf("2020-12-01"), Date.valueOf("2022-12-29")))).append("\", \"");

                if (random(0, 3) > 0) {
                    sql.append(MoneyTransferStatus.SUCCESS).append("\", \"");
                } else {
                    sql.append(MoneyTransferStatus.CANCELLED).append("\", \"");
                }

                sql.append(MoneyTransferType.TOP_UP).append("\", \"")
                        .append(walletId)
                        .append("\");");
                sqls.add(sql.toString());
            }

        }

        for (UUID walletId : walletIds) {
            int rand = random(1, 5);
            for (int j = 0; j < rand; j++) {
                UUID moneyTransferId = UUID.randomUUID();
                StringBuilder sql = new StringBuilder("INSERT INTO money_transfer (id, amount, date, status, type, wallet_id) VALUES (\"");
                sql.append(moneyTransferId).append("\", ")
                        .append(random(50, 500) * 1000).append(", \"")
                        .append(sdf.format(faker.date().between(Date.valueOf("2020-12-01"), Date.valueOf("2022-12-29")))).append("\", \"");

                if (random(0, 3) > 0) {
                    sql.append(MoneyTransferStatus.SUCCESS).append("\", \"");
                } else {
                    sql.append(MoneyTransferStatus.CANCELLED).append("\", \"");
                }

                sql.append(MoneyTransferType.WITHDRAW).append("\", \"")
                        .append(walletId)
                        .append("\");");
                sqls.add(sql.toString());
            }

        }
        String output = "";
        for (String e: sqls) {
//            System.out.print(e);
            output = output + e;
        }
        return output;
    }

//    @Test
    String createBill() {
//        createRentContract();
        billIds = new ArrayList<>();
        List<String> sqls = new ArrayList<>();
        for (UUID rentContractId : rentContractIds) {
            int rand = random(1, 20);
            for (int j = 0; j < rand; j++) {
                UUID billId = UUID.randomUUID();
                billIds.add(billId);
                StringBuilder sql = new StringBuilder("INSERT INTO bill (id, value, date, status, rent_contract_id) VALUES (\"");
                sql.append(billId).append("\", ")
                        .append(random(1000, 20000) * 1000).append(", \"")
                        .append(sdf.format(faker.date().between(Date.valueOf("2020-12-01"), Date.valueOf("2022-12-29")))).append("\", \"");

                if (random(0, 3) > 0) {
                    sql.append(BillStatus.PAID).append("\", \"");
                } else {
                    sql.append(BillStatus.UNPAID).append("\", \"");
                }

                sql.append(rentContractId)
                        .append("\");");
                sqls.add(sql.toString());
            }

        }
        String output = "";
        for (String e: sqls) {
//            System.out.print(e);
            output = output + e;
        }
        return output;
    }

//    @Test
//    not yet
    void createTransaction() {

    }

//    @Test
    String createBillItem() {
//        createBill();
//        createService();

        List<String> sqls = new ArrayList<>();
        for (UUID billId : billIds) {
            int rand = random(1, 5);
            int serviceSize = serviceIds.size() - 1;
            for (int j = 0; j < rand; j++) {
                UUID billItemId = UUID.randomUUID();
                StringBuilder sql = new StringBuilder("INSERT INTO bill_item (id, quantity, value, bill_id, service_id) VALUES (\"");
                sql.append(billItemId).append("\", ")
                        .append(random(1,4)).append(", ")
                        .append(random(50, 500) * 1000).append(", \"")
                        .append(billId).append("\", \"")
                        .append(serviceIds.get(random(0, serviceSize - 1))).append("\");");


                sqls.add(sql.toString());
            }

        }
        String output = "";
        for (String e: sqls) {
//            System.out.print(e);
            output = output + e;
        }
        return output;
    }

//    @Test
    void createData() {

        writeToFile(
                "src\\main\\resources\\import.txt",
                createAdmin() + "\n\n" + createFlatType() + "\n\n" +
                        createService() + "\n\n" + createCustomer() + "\n\n" +
                        createBuilding() + "\n\n" + createFlat() + "\n\n" +
                        createFacility() + "\n\n" +
                        createFlatFacility() + "\n\n" + createWallet() + "\n\n" +
                        createRentContract() + "\n\n" + createMoneyTransfer() + "\n\n" +
                        createBill() + "\n\n" + createBillItem()
        );

    }
    private int random(int min, int max) {
        return min + (int) (Math.random() * (max-min+1));
    }

    private void writeToFile(String fileName, String content) {
        try{
            FileWriter myWriter = new FileWriter(fileName);
            myWriter.write(content);
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}