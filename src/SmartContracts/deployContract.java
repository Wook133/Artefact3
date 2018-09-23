package SmartContracts;

import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.Contract;
import org.web3j.tx.ManagedTransaction;

import java.math.BigInteger;

public class deployContract {

    public static void main(String[] args) throws Exception {
        new deployContract().run();
    }


    private void run() throws Exception {
        Web3j web3j = Web3j.build(new HttpService());
        System.out.println("Connected to Ethereum client version: " +  web3j.web3ClientVersion().send().getWeb3ClientVersion().toString());
        Credentials credentials =
                WalletUtils.loadCredentials(
                        "123456789",
                        "W://ETH_TEST_NET//chaindata//keystore//UTC--2018-09-16T12-55-19.150022300Z--e9c2a56b8a3a29c8c85960bb0037b786e5b85e56");
        System.out.println("Credentials loaded");
        System.out.println("Deploying smart contract");
        DeVillChain_sol_deVillChain contract1 = DeVillChain_sol_deVillChain.deploy(
                web3j, credentials, ManagedTransaction.GAS_PRICE, Contract.GAS_LIMIT, new BigInteger("100000")
        ).send();
        String contractAddress1 = contract1.getContractAddress();
        System.out.println("Smart contract DeVillChain_sol_deVillChain " + contractAddress1);
    }
}
