function search() {
  const fli = document.getElementById("fromLocation");
  const fromLocation = fli.options[fli.selectedIndex].value;
  const tli = document.getElementById("fromLocation");
  const toLocation = tli.options[tli.selectedIndex].value;
  const date = Date.parse(document.getElementById("tripDate").value);

  window.location.href=`/results?tripDate=${date}&fromLocation=${fromLocation}&toLocation=${toLocation}`;
}
