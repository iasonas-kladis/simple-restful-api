function post_data()
{
  var name = document.getElementById("name").value;
  var surname = document.getElementById("surname").value;
  var amount = document.getElementById("amount").value;
  var currency = document.getElementById("currency").value;

  var query_tmp = {"name" : name, "surname" : surname, "amount" : amount, "currency" : currency};
  var query = JSON.stringify(query_tmp);
  if(name != "" && surname != "" && amount != "" && currency != "")
  {
    $.ajax({
        method: 'POST',
        url: 'http://localhost:4567/data',
        data: query,
        success: function (data) {
          alert("Data added succesfully");
        }
      });
  }
  else
  {
    alert("Please complete the input");
  }
}

function get_data()
{
  $.ajax({
      method: 'GET',
      url: 'http://localhost:4567/data',
      dataType: 'json',
      success: function (data) {
        $("#results_table").addClass("d-table");
        $("#body tr").remove();
        for (var i = 0, len = data.length; i < len; i++)
        {
          var table = document.getElementById('results_table').getElementsByTagName('tbody')[0];
          var newRow  = table.insertRow(table.rows.length);
          var newCell0  = newRow.insertCell(0);
          var newCell1  = newRow.insertCell(1);
          var newCell2  = newRow.insertCell(2);
          var newCell3  = newRow.insertCell(3);

          var newText0  = document.createTextNode(data[i].name)
          newCell0.appendChild(newText0);
          var newText1  = document.createTextNode(data[i].surname)
          newCell1.appendChild(newText1);
          var newText2  = document.createTextNode(data[i].amount)
          newCell2.appendChild(newText2);
          var newText3  = document.createTextNode(data[i].currency)
          newCell3.appendChild(newText3);
        }
      }
    });

    document.getElementById('export').addEventListener('click', exportPDF);
    function exportPDF()
    {
      var doc = new jsPDF('p', 'pt', 'a4');
      var source = document.getElementById('results').innerHTML;
      var margins = {
        top: 10,
        bottom: 10,
        left: 10,
        width: 600
      };

      doc.fromHTML(
        source,
        margins.left,
        margins.top, {
          'width': margins.width
        },

        function(dispose)
        {
          doc.save('results.pdf');
        }, margins);
    }
}
