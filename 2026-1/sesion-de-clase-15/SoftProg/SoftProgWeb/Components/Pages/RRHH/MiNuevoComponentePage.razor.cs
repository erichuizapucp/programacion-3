using Microsoft.AspNetCore.Components;
using SoftProgNegocio.Bo.Rrhh;
using SoftProgWeb.ViewModels;

namespace SoftProgWeb.Components.Pages.RRHH
{ 
    public partial class MiNuevoComponentePage : ComponentBase
    {
        [Inject] public IAreaBo AreaBo { get; set; } = default!;
        [Inject] public NavigationManager NavigationManager { get; set; }

        public List<AreaViewModel> Areas = new();

        protected override void OnInitialized()
        {
            var areas = AreaBo.Listar();
            foreach (var area in areas)
            {
                Areas.Add(new AreaViewModel()
                {
                    Id = area.Id,
                    Nombre = area.Nombre,
                    Activo = area.Activo,
                });
            }
        }

        protected override void OnParametersSet()
        {
            
        }
    }
}
