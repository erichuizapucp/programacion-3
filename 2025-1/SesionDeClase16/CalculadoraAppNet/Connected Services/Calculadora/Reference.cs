﻿//------------------------------------------------------------------------------
// <auto-generated>
//     This code was generated by a tool.
//     Runtime Version:4.0.30319.42000
//
//     Changes to this file may cause incorrect behavior and will be lost if
//     the code is regenerated.
// </auto-generated>
//------------------------------------------------------------------------------

namespace CalculadoraAppNet.Calculadora {
    
    
    [System.CodeDom.Compiler.GeneratedCodeAttribute("System.ServiceModel", "4.0.0.0")]
    [System.ServiceModel.ServiceContractAttribute(Namespace="http://ws.sesion16.inf30.pucp.edu.pe/", ConfigurationName="Calculadora.Calculadora")]
    public interface Calculadora {
        
        // CODEGEN: Generating message contract since message part namespace () does not match the default value (http://ws.sesion16.inf30.pucp.edu.pe/)
        [System.ServiceModel.OperationContractAttribute(Action="http://ws.sesion16.inf30.pucp.edu.pe/Calculadora/sumarRequest", ReplyAction="http://ws.sesion16.inf30.pucp.edu.pe/Calculadora/sumarResponse")]
        [return: System.ServiceModel.MessageParameterAttribute(Name="return")]
        CalculadoraAppNet.Calculadora.sumarResponse sumar(CalculadoraAppNet.Calculadora.sumarRequest request);
        
        [System.ServiceModel.OperationContractAttribute(Action="http://ws.sesion16.inf30.pucp.edu.pe/Calculadora/sumarRequest", ReplyAction="http://ws.sesion16.inf30.pucp.edu.pe/Calculadora/sumarResponse")]
        System.Threading.Tasks.Task<CalculadoraAppNet.Calculadora.sumarResponse> sumarAsync(CalculadoraAppNet.Calculadora.sumarRequest request);
    }
    
    [System.Diagnostics.DebuggerStepThroughAttribute()]
    [System.CodeDom.Compiler.GeneratedCodeAttribute("System.ServiceModel", "4.0.0.0")]
    [System.ComponentModel.EditorBrowsableAttribute(System.ComponentModel.EditorBrowsableState.Advanced)]
    [System.ServiceModel.MessageContractAttribute(WrapperName="sumar", WrapperNamespace="http://ws.sesion16.inf30.pucp.edu.pe/", IsWrapped=true)]
    public partial class sumarRequest {
        
        [System.ServiceModel.MessageBodyMemberAttribute(Namespace="", Order=0)]
        public int a;
        
        [System.ServiceModel.MessageBodyMemberAttribute(Namespace="", Order=1)]
        public int b;
        
        public sumarRequest() {
        }
        
        public sumarRequest(int a, int b) {
            this.a = a;
            this.b = b;
        }
    }
    
    [System.Diagnostics.DebuggerStepThroughAttribute()]
    [System.CodeDom.Compiler.GeneratedCodeAttribute("System.ServiceModel", "4.0.0.0")]
    [System.ComponentModel.EditorBrowsableAttribute(System.ComponentModel.EditorBrowsableState.Advanced)]
    [System.ServiceModel.MessageContractAttribute(WrapperName="sumarResponse", WrapperNamespace="http://ws.sesion16.inf30.pucp.edu.pe/", IsWrapped=true)]
    public partial class sumarResponse {
        
        [System.ServiceModel.MessageBodyMemberAttribute(Namespace="", Order=0)]
        public int @return;
        
        public sumarResponse() {
        }
        
        public sumarResponse(int @return) {
            this.@return = @return;
        }
    }
    
    [System.CodeDom.Compiler.GeneratedCodeAttribute("System.ServiceModel", "4.0.0.0")]
    public interface CalculadoraChannel : CalculadoraAppNet.Calculadora.Calculadora, System.ServiceModel.IClientChannel {
    }
    
    [System.Diagnostics.DebuggerStepThroughAttribute()]
    [System.CodeDom.Compiler.GeneratedCodeAttribute("System.ServiceModel", "4.0.0.0")]
    public partial class CalculadoraClient : System.ServiceModel.ClientBase<CalculadoraAppNet.Calculadora.Calculadora>, CalculadoraAppNet.Calculadora.Calculadora {
        
        public CalculadoraClient() {
        }
        
        public CalculadoraClient(string endpointConfigurationName) : 
                base(endpointConfigurationName) {
        }
        
        public CalculadoraClient(string endpointConfigurationName, string remoteAddress) : 
                base(endpointConfigurationName, remoteAddress) {
        }
        
        public CalculadoraClient(string endpointConfigurationName, System.ServiceModel.EndpointAddress remoteAddress) : 
                base(endpointConfigurationName, remoteAddress) {
        }
        
        public CalculadoraClient(System.ServiceModel.Channels.Binding binding, System.ServiceModel.EndpointAddress remoteAddress) : 
                base(binding, remoteAddress) {
        }
        
        [System.ComponentModel.EditorBrowsableAttribute(System.ComponentModel.EditorBrowsableState.Advanced)]
        CalculadoraAppNet.Calculadora.sumarResponse CalculadoraAppNet.Calculadora.Calculadora.sumar(CalculadoraAppNet.Calculadora.sumarRequest request) {
            return base.Channel.sumar(request);
        }
        
        public int sumar(int a, int b) {
            CalculadoraAppNet.Calculadora.sumarRequest inValue = new CalculadoraAppNet.Calculadora.sumarRequest();
            inValue.a = a;
            inValue.b = b;
            CalculadoraAppNet.Calculadora.sumarResponse retVal = ((CalculadoraAppNet.Calculadora.Calculadora)(this)).sumar(inValue);
            return retVal.@return;
        }
        
        [System.ComponentModel.EditorBrowsableAttribute(System.ComponentModel.EditorBrowsableState.Advanced)]
        System.Threading.Tasks.Task<CalculadoraAppNet.Calculadora.sumarResponse> CalculadoraAppNet.Calculadora.Calculadora.sumarAsync(CalculadoraAppNet.Calculadora.sumarRequest request) {
            return base.Channel.sumarAsync(request);
        }
        
        public System.Threading.Tasks.Task<CalculadoraAppNet.Calculadora.sumarResponse> sumarAsync(int a, int b) {
            CalculadoraAppNet.Calculadora.sumarRequest inValue = new CalculadoraAppNet.Calculadora.sumarRequest();
            inValue.a = a;
            inValue.b = b;
            return ((CalculadoraAppNet.Calculadora.Calculadora)(this)).sumarAsync(inValue);
        }
    }
}
