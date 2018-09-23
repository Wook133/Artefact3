pragma solidity ^0.4.25;

contract deVillChain {
    address contractOwner;//owner of the contract
    uint256 capital;


    uint256 countUsers; // count for mapping purposes
    mapping (uint256 => address) mapUsers; // all users, mapped using countUsers

    uint256 creationDate;
    //mapping (address => mapping(uint256 => struct))

    mapping (address => mapping(uint256 => source)) mapUserSources;

    constructor()
    payable
    public
    {
        contractOwner = msg.sender;
        capital = msg.value;
        countUsers = 0;
    }

    //Modifiers
    modifier isOwner() {
        require(contractOwner == msg.sender, "Not Owner of Contract");
        _;
    }
    modifier userIndexOutofBounds(uint256 i)
    {
        require(i <= countUsers, "User Index Out of Bounds");
        _;
    }

    function addUser()
    {
        mapUsers[countUsers] = msg.sender;
        countUsers = countUsers + 1;
    }
    function getUser(uint256 i)
    public
    userIndexOutofBounds(i)
    returns (address)
    {
        return mapUsers[i];
    }




    struct source
    {
        string thishash;
        string hashoffile;
        uint64 timestamp;
        //address poster; // captured with map i.e. when adding
        string originalUrl;
        uint256 countadditional;
        mapping (uint256 => supplemtarysource) mapadditional;
    }

    struct supplementarysource //only if hashoffiles are identical
    {
        uint64 timestamp;
        string additionalUrl;
        string hashoffile;
    }

    function deVillChain(){

    }

    function closeContract()
    isOwner
    public
    returns (bool)
    {
        selfdestruct(contractOwner);
        return true;
    }
}
