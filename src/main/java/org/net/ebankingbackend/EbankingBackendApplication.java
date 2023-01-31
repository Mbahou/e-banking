package org.net.ebankingbackend;

import org.net.ebankingbackend.dtos.BankAccountDto;
import org.net.ebankingbackend.dtos.CurrentBankAccoutDto;
import org.net.ebankingbackend.dtos.CustomerDto;
import org.net.ebankingbackend.dtos.SavingBankAccoutDto;
import org.net.ebankingbackend.entites.*;
import org.net.ebankingbackend.enums.AccountStatus;
import org.net.ebankingbackend.enums.OperationType;
import org.net.ebankingbackend.exeptions.BalanceNotSufficentException;
import org.net.ebankingbackend.exeptions.BanAccountNotFoundExecption;
import org.net.ebankingbackend.exeptions.CustomerNotFoundExeception;
import org.net.ebankingbackend.repositories.BankAccountRepository;
import org.net.ebankingbackend.repositories.BankOperationRepository;
import org.net.ebankingbackend.repositories.CustomerRepository;
import org.net.ebankingbackend.services.BankAccountService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

@SpringBootApplication
public class EbankingBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(EbankingBackendApplication.class, args);
	}
	@Bean
	CommandLineRunner start(BankAccountService bankAccountService) {
		return args -> {
			Stream.of("Hassan","Imane","Mohamed").forEach(name->{
				CustomerDto customer=new CustomerDto();
				customer.setName(name);
				customer.setEmail(name+"@gmail.com");
				bankAccountService.saveCustomer(customer);
			});
			bankAccountService.listCustomers().forEach(customer->{
				try {
					bankAccountService.saveCurrentBankAccount(Math.random()*90000,customer.getId(),9000);
					bankAccountService.saveSavingBankAccount(Math.random()*120000,customer.getId(),5.5);

				} catch (CustomerNotFoundExeception e) {
					e.printStackTrace();
				}
			});
			List<BankAccountDto> bankAccounts = bankAccountService.bankaccontlist();
			for (BankAccountDto bankAccount:bankAccounts){

				for (int i = 0; i <10 ; i++) {
					String accountId;
					if(bankAccount instanceof SavingBankAccoutDto){
						accountId=((SavingBankAccoutDto) bankAccount).getId();
					} else{
						accountId=((CurrentBankAccoutDto) bankAccount).getId();
					}
					bankAccountService.credit(accountId,10000+Math.random()*120000,"Credit");
					bankAccountService.debit(accountId,1000+Math.random()*9000,"Debit");
				}
			}


		};
	}








		/*	BankAccout bankAccout =
					bankAccountRepository.findById("050d81a7-a567-4349-8ba9-577fca2518d8").orElse(null);
			System.out.println("***************************************");
			System.out.println(bankAccout.getId());
			System.out.println(bankAccout.getBalance());
			System.out.println(bankAccout.getAccountStatus());
			System.out.println(bankAccout.getCreatedAt());
			System.out.println(bankAccout.getCustomer().getName());
			System.out.println(bankAccout.getClass().getSimpleName());
			if (bankAccout instanceof CurrentAccount){
				System.out.println("Over Draft=> "+((CurrentAccount)bankAccout).getOverDraft());
			} else if (bankAccout instanceof SavingAccount) {
				System.out.println("Rate =>"+((SavingAccount)bankAccout).getInterestRate());

			}
			bankAccout.getBankOperations().forEach(op->{
				System.out.println("======================================");
				System.out.println(op.getType() +"/t"+ op.getOperationdate() +"/t"+op.getAmount() );

			});

		 */


	//@Bean
	CommandLineRunner start(CustomerRepository customerRepository, BankAccountRepository bankAccountRepository, BankOperationRepository bankOperationRepository){
		return (args -> {
			Stream.of("Hassan","Mehdi","Yahya").forEach(name->{
				Customer customer=new Customer();
				customer.setName(name);
				customer.setEmail(name+"@gmail.com");
				customerRepository.save(customer);


			});

			customerRepository.findAll().forEach(cust->{
				CurrentAccount currentAccount=new CurrentAccount();
				currentAccount.setId(UUID.randomUUID().toString());
				currentAccount.setBalance(Math.random()*9000);
				currentAccount.setCreatedAt(new Date());
				currentAccount.setAccountStatus(AccountStatus.CREATED);
				currentAccount.setCustomer(cust);
				currentAccount.setOverDraft(9000);
				bankAccountRepository.save(currentAccount);


				SavingAccount savingAccount=new SavingAccount();
				savingAccount.setId(UUID.randomUUID().toString());
				savingAccount.setBalance(Math.random()*9000);
				savingAccount.setCreatedAt(new Date());
				savingAccount.setAccountStatus(AccountStatus.CREATED);
				savingAccount.setCustomer(cust);
				savingAccount.setInterestRate(4.5);
				bankAccountRepository.save(savingAccount);

			});
			bankAccountRepository.findAll().forEach(acc->{
				for (int i = 0; i < 5; i++) {
					BankOperation bankOperation=new BankOperation();
					bankOperation.setBankAccout(acc);
					bankOperation.setOperationdate(new Date());
					bankOperation.setAmount(Math.random()*12000);
					bankOperation.setType(Math.random()>0.5? OperationType.Debit:OperationType.CREDIT);
					bankOperationRepository.save(bankOperation);

				}


			});



		});



		};

	}


