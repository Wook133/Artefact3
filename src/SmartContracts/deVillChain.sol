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

    modifier userExists(address a){
        bool b = false;
        uint256 i = 0;
        while ((b==false) && (i <= countUsers))
        {
            if (mapUsers[i] == a)
            {
                b = true;
                i = i -1;
            }
            i = i+1;
        }
        require(b, "User does not exist");
        _;
    }

    modifier sourceExists(uint256 i, address a)
    {
        bool b = false;
        if (i <= mapCountUserSources[a])
        {
            b = true;
        }
        require(b, "User's source does not exist");
        _;
    }

    modifier additionalsourceExists(uint256 i, uint256 j, address a)
    {
        bool b = false;
        if (i <= mapUserSources[a][mapCountUserSources[a]].countadditional)
        {
            b = true;
        }
        require(b, "User's source or additional source does not exist");
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

    function getSource(address a, uint256 i)
        //userExists(a)
    view
    public
    sourceExists(i, a)
    returns (string, string, uint64, string, uint256)
    {
        return (mapUserSources[a][i].thishash,
        mapUserSources[a][i].hashoffile,
        mapUserSources[a][i].timestamp,
        mapUserSources[a][i].originalUrl,
        mapUserSources[a][i].countadditional);
    }

    function getHash(address a, uint256 i)
    view
    public
    userExists(a)
    sourceExists(i, a)
    returns (string)
    {
        return (mapUserSources[a][i].thishash);
    }
    function getHashofFile(address a, uint256 i)
    view
    public
    userExists(a)
    sourceExists(i, a)
    returns (string)
    {
        return (mapUserSources[a][i].hashoffile);
    }

    function getUrl(address a, uint256 i)
    view
    public
    userExists(a)
    sourceExists(i, a)
    returns (string)
    {
        return (mapUserSources[a][i].originalUrl);
    }

    function getTimestamp(address a, uint256 i)
        //userExists(a)
    view
    public
    userExists(a)
    sourceExists(i, a)
    returns (uint64)
    {
        return (mapUserSources[a][i].timestamp);
    }

    function getSourceCount(address a, uint256 i)
        //userExists(a)
    view
    public
    userExists(a)
    sourceExists(i, a)
    returns (uint256)
    {
        return (mapUserSources[a][i].countadditional);
    }

    //add additional if hof is identical
    function addAdditionalSourceLatest(string _hof, uint64 _ts, string _au)
    payable
    public
    returns (bool)
    {
        if (compareStrings(_hof, mapUserSources[msg.sender][mapCountUserSources[msg.sender]].hashoffile))
        {
            uint256 i = mapCountUserSources[msg.sender];
            uint256 j = mapUserSources[msg.sender][mapCountUserSources[msg.sender]].countadditional;
            mapUserSources[msg.sender][i].mapadditional[j].timestamp = _ts;
            mapUserSources[msg.sender][i].mapadditional[j].additionalUrl = _au;
            mapUserSources[msg.sender][i].mapadditional[j].hashoffile = _hof;
            mapUserSources[msg.sender][mapCountUserSources[msg.sender]].countadditional = mapUserSources[msg.sender][mapCountUserSources[msg.sender]].countadditional + 1;
            return true;
        }
        else
        {
            return false;
        }
    }

    function compareStrings (string a, string b) public pure returns (bool){
        return keccak256(string_tobytes(a)) == keccak256(string_tobytes(b));
    }

    function string_tobytes( string s) pure public returns (bytes){
        bytes memory b3 = bytes(s);
        return b3;
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
    {
        selfdestruct(contractOwner);
    }
}
