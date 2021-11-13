    
    function increment(id) {
        var medicines = document.getElementsByTagName("tr");
        // console.log(medicines);

        for(let i=1; i<medicines.length; i++)
        {
            var med = medicines[i].children;

            // for(let j=0; j<med.length; j++)
            // {
            //     console.log(med[j]);
            // }
            console.log(med);

            console.log(med[1]);
            console.log(med[4]);

            if(med[1].id == id)
                med[4].innerHTML = (parseInt(med[4].innerHTML) + 1).toString();


            // if()
        }
    }

    function decrement(id) {
        var medicines = document.getElementsByTagName("tr");
        console.log(medicines.children);
    }