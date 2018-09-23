pragma solidity ^0.4.25;

contract deVillChain {
    address contractOwner;//owner of the contract
    uint256 capital;


    uint256 countUsers; // count for mapping purposes
    mapping (uint256 => address) mapUsers; // all users, mapped using countUsers

    uint256 creationDate;
    //mapping (address => mapping(uint256 => struct))

    mapping (address => uint256) mapCountUserSources;
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
    payable
    public
    returns (bool)
    {
        mapUsers[countUsers] = msg.sender;
        countUsers = countUsers + 1;
        mapCountUserSources[msg.sender] = 0;
        return true;
    }
    function getUser(uint256 i)
    payable
    public
    userIndexOutofBounds(i)
    returns (address)
    {
        return mapUsers[i];
    }

    function addSource(string _th, string _hof, uint64 _ts, string _ou)
    payable
    public
    returns (bool)
    {
        mapUserSources[msg.sender][mapCountUserSources[msg.sender]].thishash = _th;
        mapUserSources[msg.sender][mapCountUserSources[msg.sender]].hashoffile = _hof;
        mapUserSources[msg.sender][mapCountUserSources[msg.sender]].timestamp = _ts;
        mapUserSources[msg.sender][mapCountUserSources[msg.sender]].originalUrl = _ou;
        mapUserSources[msg.sender][mapCountUserSources[msg.sender]].countadditional = 0;
        mapCountUserSources[msg.sender] = mapCountUserSources[msg.sender]+1;
        return true;
    }

    function addAdditionalSourceLatest(string _hof, uint64 _ts, string _au)
    payable
    public
    returns (bool)
    {
        uint256 i = mapCountUserSources[msg.sender];
        uint256 j = mapUserSources[msg.sender][mapCountUserSources[msg.sender]].countadditional;
        mapUserSources[msg.sender][i].mapadditional[j].timestamp = _ts;
        mapUserSources[msg.sender][i].mapadditional[j].additionalUrl = _au;
        mapUserSources[msg.sender][i].mapadditional[j].hashoffile = _hof;
        mapUserSources[msg.sender][mapCountUserSources[msg.sender]].countadditional = mapUserSources[msg.sender][mapCountUserSources[msg.sender]].countadditional + 1;
        return true;
    }




    struct source
    {
        string thishash;
        string hashoffile;
        uint64 timestamp;
        //address poster; // captured with map i.e. when adding
        string originalUrl;
        uint256 countadditional;
        mapping (uint256 => supplementarysource) mapadditional;
    }

    struct supplementarysource //only if hashoffiles are identical
    {
        uint64 timestamp;
        string additionalUrl;
        string hashoffile;
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
