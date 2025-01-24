function submitClick(btn) {
    var test = '';
}


$(document).ready(function () {

    const fileUpload = document.querySelector("#input-file-form");

    fileUpload.addEventListener('change', function (event) {
            var file = this.files[0];
            let reader = new FileReader();


            reader.onload = function (e) {
                var title = document.getElementById('output-file-title');
                var embed = document.getElementById('output-file-embed');
                var excel = document.getElementById('output-file-excel');
                var other = document.getElementById('output-file-form');
                var image = document.getElementById('output-file-image');

                other.style.display = 'none';
                embed.style.display = 'none';
                excel.style.display = 'none';
                image.style.display = 'none';
                title.innerHTML = '';

                if (file.type.includes('image')) {
                    title.innerHTML = 'Image input :';
                    image.style.display = 'block';
                    image.src = e.target.result;
                } else if (file.type.includes('pdf')) {
                    title.innerHTML = 'PDF input :';
                    embed.style.display = 'block';
                    embed.src = reader.result;
                } else if (file.type.includes('application/vnd.openxmlformats-officedocument.spreadsheetml.sheet')) {
                    title.innerHTML = 'Excel input :';
                    excel.style.display = 'block';

                    var data = new Uint8Array(reader.result);
                    var work_book = XLSX.read(data, {type: 'array'});
                    var sheet_name = work_book.SheetNames;
                    var sheet_data = XLSX.utils.sheet_to_json(work_book.Sheets[sheet_name[0]], {header: 1});
                    if (sheet_data.length > 0) {
                        var table_output = '<table class="table table-striped table-bordered">';
                        for (var row = 0; row < sheet_data.length; row++) {
                            table_output += '<tr>';
                            for (var cell = 0; cell < sheet_data[row].length; cell++) {
                                if (row == 0) {
                                    table_output += '<th>' + sheet_data[row][cell] + '</th>';
                                }
                                else {
                                    table_output += '<td>' + sheet_data[row][cell] + '</td>';
                                }
                            }
                            table_output += '</tr>';
                        }
                        table_output += '</table>';
                        excel.innerHTML = table_output;
                    }
                }
                else {
                    title.innerHTML = 'File input :';
                    other.style.display = 'block';
                    other.textContent = reader.result;
                }


            }
            if (file.type.includes('image')) {
                reader.readAsDataURL(file);
            } else if (file.type.includes('pdf')) {
                reader.readAsDataURL(file);
            } else if (file.type.includes('application/vnd.openxmlformats-officedocument.spreadsheetml.sheet')) {
                reader.readAsArrayBuffer(file);
            } else {
                reader.readAsText(file);
            }
        }
    );
});

