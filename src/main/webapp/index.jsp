<!DOCTYPE html>
<html>
<head>
    <script
      src="http://code.jquery.com/jquery-1.9.1.min.js">
    </script>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css">
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>

    <script type="text/javascript">
        $(function() {
            $("#data-type").on('change', function() {

                var type = $(this).find(':selected').get(0).value;

                $.get(
                    "<%= request.getContextPath() %>/process",
                    {type: type}
                ).done(function(data) {
                    console.log(data);
                });

            });
        });
    </script>
</head>
  <body>
    <div class="container">
        <select class="form-control" id="data-type">
          <option></option>
          <option value="received">Received transactions</option>
          <option value="void">Void transactions</option>
          <option value="with-exceptions">transactions with exceptions</option>
        </select>
    </div>
  </body>
</html>